# Kotlin value class 를 사용하려면 어떻게 해야할까요?

엔티티의 프로퍼티를 kotlin의 [`value class`](https://kotlinlang.org/docs/inline-classes.html)로 선언할 수 있습니다.

```kotlin
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UserId = UserId(0),
)

@JvmInline
value class UserId(private val value: Long)

@Service
class UserService(
    private val jpqlRenderContext: JpqlRenderContext,
    private val entityManager: EntityManager,
) {

    fun findById(userId: UserId): User? {
        val query = jpql {
            select(
                entity(User::class)
            ).from(
                entity(User::class),
            ).where(
                path(User::id).equal(userId)
            )
        }

        return entityManager.createQuery(query, jpqlRenderContext).apply { maxResults = 1 }.resultList.firstOrNull()
    }
}
```

하지만 추가적인 설정 없이 Hibernate를 사용해 Kotlin JDSL을 통해 조회하면 에러가 발생합니다.

```
org.hibernate.type.descriptor.java.CoercionException: Cannot coerce value 'UserId(value=1)' [com.example.entity.UserId] to Long
...
```

이를 해결하려면 Kotlin JDSL이 매개 변수로 전달되는 `value class`의 unboxing이 필요합니다.
unboxing은 다음 방안 중 하나를 선택해서 수행할 수 있습니다.

### JpqlValue용 커스텀 JpqlSerializer

에러를 해결하기 위해 `EntityManager`에 인자들을 `value class` 그 자체로 넘기지 않고 unboxing한 값을 넘겨야합니다.
Kotlin JDSL은 `JpqlValueSerializer` 클래스에서 인자들을 추출하는 역할을 담당합니다.
따라서 기본 제공하는 클래스 대신 커스텀 Seriailzer를 등록해야 합니다.

먼저 다음과 같은 커스텀 Seriailzer를 생성합니다.

```kotlin
class ValueClassAwareJpqlValueSerializer(
    private val delegate: JpqlValueSerializer,
) : JpqlSerializer<JpqlValue<*>> {
    override fun handledType(): KClass<JpqlValue<*>> {
        return JpqlValue::class
    }

    override fun serialize(
        part: JpqlValue<*>,
        writer: JpqlWriter,
        context: RenderContext,
    ) {
        val value = part.value

        if (value::class.isValue) {
            writer.writeParam(value::class.memberProperties.first().getter.call(value))
            return
        }

        delegate.serialize(part, writer, context)
    }
}
```

이제 이 클래스를 `RenderContext`에 추가해야 합니다.
추가하는 방법은 [다음 문서](../jpql-with-kotlin-jdsl/custom-dsl.md#serializer)를 참조할 수 있습니다.
만약 스프링 부트를 사용하는 경우 다음과 같은 코드를 통해 커스텀 Seriziler를 Bean으로 등록하면 됩니다.

```kotlin
@Configuration
class CustomJpqlRenderContextConfig {
    @Bean
    fun jpqlSerializer(): JpqlSerializer<*> {
        return ValueClassAwareJpqlValueSerializer(JpqlValueSerializer())
    }
}
```

### custom method 사용

JDSL에서 제공하는 [custom dsl](../jpql-with-kotlin-jdsl/custom-dsl.md#dsl) 사용해 value class 에 사용되는 매서드를 추가할 수 있습니다.

```kotlin
class CustomJpql : Jpql() {
    fun Expressionable<UserId>.equalValue(value: UserId): Predicate {
        return Predicates.equal(this.toExpression(), Expressions.value(value.value))
    }
}

val query = jpql(CustomJpql) {
    select(
        entity(User::class)
    ).from(
        entity(User::class),
    ).where(
        path(User::id).equalValue(userId)
    )
}
```

interface 도입과 오버로딩을 통해 다양한 value class에 대응할 수 있습니다.

```kotlin
interface PrimaryLongId { val value: Long }

value class UserId(override val value: Long) : PrimaryLongId

class CustomJpql : Jpql() {
    fun <T: PrimaryLongId> Expressionable<T>.equal(value: T): Predicate {
        return Predicates.equal(this.toExpression(), Expressions.value(value.value))
    }
}
```

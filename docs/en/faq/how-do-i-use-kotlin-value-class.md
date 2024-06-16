# How do I use Kotlin value class

You can declare the properties of an entity as [`value class`](https://kotlinlang.org/docs/inline-classes.html) in kotlin.

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

However, if you use Hibernate to query through Kotlin JDSL without extra configuration, you will get an error.

```
org.hibernate.type.descriptor.java.CoercionException: Cannot coerce value 'UserId(value=1)' [com.example.entity.UserId] to Long
...
```

To solve this, Kotlin JDSL requires unboxing of the `value class` passed as a parameter.
Unboxing can be done in one of the following ways

### Custom JpqlSerializer for JpqlValue

To resolve the error, you need to pass the unboxed value to the `EntityManager` instead of passing the arguments to the `value class` itself.
Kotlin JDSL is supposed to extract the arguments from the `JpqlValueSerializer` class.
So, we need to register a custom Seriailzer instead of the built-in class.

First, create the following custom Seriailzer.

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

Now we need to add this class to our `RenderContext`.
You can refer to [the following documentation](../jpql-with-kotlin-jdsl/custom-dsl.md#serializer) for how to add it.
If you are using Spring Boot, you can register your custom Seriziler as a bean with the following code.

```kotlin
@Configuration
class CustomJpqlRenderContextConfig {
    @Bean
    fun jpqlSerializer(): JpqlSerializer<*> {
        return ValueClassAwareJpqlValueSerializer(JpqlValueSerializer())
    }
}
```

### Custom method

You can use the [custom dsl](../jpql-with-kotlin-jdsl/custom-dsl.md#dsl) provided by Kotlin JDSL to add methods used in the value class.

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

interfaces and overloading to support different value classes.

```kotlin
interface PrimaryLongId { val value: Long }

value class UserId(override val value: Long) : PrimaryLongId

class CustomJpql : Jpql() {
    fun <T: PrimaryLongId> Expressionable<T>.equal(value: T): Predicate {
        return Predicates.equal(this.toExpression(), Expressions.value(value.value))
    }
}
```

### Notes for DTO Projection

If you use value class in DTO Projection, it is not supported if the property is nullable.
Therefore, rather than using value class directly in DTO Projection, it is recommended to use the default type and convert it after querying.

```kotlin
data class ResponseDto(
    private val rawId: Long,
) {
    val id: UserId
        get() = UserId(rawId)
}

val query = jpql(CustomJpql) {
    selectNew<ResponseDto>(
        entity(User::id)
    ).from(
        entity(User::class),
    ).where(
        path(User::id).equalValue(userId)
    )
}
```

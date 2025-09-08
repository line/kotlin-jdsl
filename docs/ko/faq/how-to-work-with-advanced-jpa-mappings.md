# 고급 JPA 매핑은 어떻게 처리하나요?

Kotlin JDSL은 다양한 JPA 매핑 전략과 원활하게 작동하도록 설계되었습니다. 다음은 복합 키, 상속, 임베디드 ID와 같은 고급 매핑이 적용된 엔티티를 쿼리하는 방법에 대한 몇 가지 예입니다.

## `@IdClass`를 사용한 복합 키

엔티티가 복합 기본 키에 `@IdClass`를 사용하는 경우, 평소와 같이 엔티티의 속성을 참조하여 쿼리할 수 있습니다.

`book`과 `authorId`로 구성된 복합 키를 가진 `BookAuthor` 엔티티를 예로 들어 보겠습니다.

```kotlin
@Entity
@IdClass(BookAuthor.BookAuthorId::class)
class BookAuthor(
    @Id
    val authorId: Long,
) {
    @Id
    @ManyToOne
    lateinit var book: Book
    // ...
}
```

`BookAuthor`를 조인하고 복합 키의 일부인 속성을 포함하여 해당 속성을 참조하는 쿼리를 작성할 수 있습니다.

```kotlin
val query = jpql {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class),
        join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
    )
}
```

## 엔티티 상속

Kotlin JDSL은 JPA 상속을 사용하는 엔티티 쿼리를 지원합니다. `treat`를 사용하여 쿼리에서 특정 하위 유형으로 다운캐스팅할 수 있습니다.

예를 들어, `Employee`가 `FullTimeEmployee`를 하위 클래스로 갖는 기본 클래스인 경우:

```kotlin
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class Employee(
    // ...
)

@Entity
class FullTimeEmployee(
    // ...
    var annualSalary: EmployeeSalary,
) : Employee(...)
```

`FullTimeEmployee`와 해당 속성을 구체적으로 대상으로 하는 쿼리를 작성할 수 있습니다.

```kotlin
val query = jpql {
    update(
        entity(FullTimeEmployee::class),
    ).set(
        path(FullTimeEmployee::annualSalary)(EmployeeSalary::value),
        path(FullTimeEmployee::annualSalary)(EmployeeSalary::value).times(BigDecimal.valueOf(1.1)),
    ).where(
        path(FullTimeEmployee::employeeId).`in`(employeeIds),
    )
}
```

`where` 절에서 엔티티 유형으로 필터링하기 위해 `type()`을 사용할 수도 있습니다.

```kotlin
val query = jpql {
    select(
        path(Employee::employeeId),
    ).from(
        entity(Employee::class),
    ).where(
        type(entity(Employee::class)).eq(FullTimeEmployee::class)
    )
}
```

## 임베디드 ID 및 임베디드 가능 유형

경로 표현식을 연결하여 `@EmbeddedId`를 사용하거나 `@Embedded` 객체를 포함하는 엔티티를 쿼리할 수 있습니다.

`Book`에 `Isbn` 유형의 `@EmbeddedId`가 있는 경우:

```kotlin
@Entity
class Book(
    @EmbeddedId
    val isbn: Isbn,
    // ...
)

@Embeddable
data class Isbn(
    val value: String,
)
```

`path(Book::isbn)(Isbn::value)`를 사용하여 `Isbn`의 `value`에 액세스할 수 있습니다.

```kotlin
val query = jpql {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    ).where(
        path(Book::isbn)(Isbn::value).eq("01"),
    )
}
```

이 접근 방식은 `@EmbeddedId`와 일반 `@Embedded` 속성 모두에 적용됩니다.

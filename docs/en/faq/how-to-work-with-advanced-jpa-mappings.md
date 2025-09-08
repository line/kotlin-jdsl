# How to work with advanced JPA mappings?

Kotlin JDSL is designed to work seamlessly with various JPA mapping strategies. Here are some examples of how to query entities with advanced mappings like composite keys, inheritance, and embedded IDs.

## Composite Keys with `@IdClass`

When an entity uses `@IdClass` for a composite primary key, you can query it by referencing the properties of the entity as usual.

Consider the `BookAuthor` entity, which has a composite key consisting of `book` and `authorId`.

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

You can write a query that joins `BookAuthor` and refers to its properties, including those that are part of the composite key.

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

## Entity Inheritance

Kotlin JDSL supports querying entities that use JPA inheritance. You can use `treat` to downcast to a specific subtype in your queries.

For example, if `Employee` is a base class with `FullTimeEmployee` as a subclass:

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

You can write a query that specifically targets `FullTimeEmployee` and its properties.

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

You can also use `type()` to filter by entity type in a `where` clause.

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

## Embedded IDs and Embeddable Types

You can query entities that use `@EmbeddedId` or contain `@Embedded` objects by chaining path expressions.

If a `Book` has an `@EmbeddedId` of type `Isbn`:

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

You can access the `value` of the `Isbn` using `path(Book::isbn)(Isbn::value)`.

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

This approach works for both `@EmbeddedId` and regular `@Embedded` properties.

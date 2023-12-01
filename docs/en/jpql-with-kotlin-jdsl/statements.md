# Statements

The JPQL supports select, update, and delete statements. Kotlin JDSL provides a DSL to build them.

## Select statement

Call `select()` in `jpql()` to build a select statement.

```kotlin
val query = jpql {
    select(
        path(Employee::employeeId),
    ).from(
        entity(Employee::class),
        join(Employee::departments),
    ).where(
        type(entity(Employee::class)).eq(FullTimeEmployee::class)
    ).groupBy(
        path(Employee::employeeId),
    ).having(
        count(Employee::employeeId).greaterThan(1L),
    ).orderBy(
        count(Employee::employeeId).desc(),
        path(Employee::employeeId).asc(),
    )
}
```

### Select clause

Use `select()` and pass [`Expression`](expressions.md) to build a select clause in the select statement.
If you pass only one `Expression` to `select()`, it will infer a return type from `Expression`.
However, if you pass more than one `Expression`, you need to specify the type as it cannot infer the type.

```kotlin
// It can infer the result type.
select(path(Author::authorId))

// It cannot infer the result type.
select(path(Author::authorId), path(Author::name))

// This allows it to know the result type.
select<CustomEntity>(path(Author::authorId), path(Author::name))
```

#### DTO projection

Specify a DTO class and pass parameters of the constructor to `selectNew()` to build a DTO projection.

```kotlin
data class Row(
    val departmentId: Long,
    val count: Long,
)

selectNew<Row>(
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
)
```

### From clause

Use `from()` and pass [Entity](entities.md) and [Join](statements.md#join) to specify the entities for selection when building a from clause in the select statement.

```kotlin
from(
    entity(Author::class),
    join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
)
```

#### Join

Use `join()` and `fetchJoin()` to combine the entities for selection.
There are two types of `join()`: Join and Association Join
This is distinguished by whether `join()` is used between two unrelated entities or between two related entities.

```kotlin
@Entity
// ...
class Book(
    // ...

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    val authors: MutableSet<BookAuthor>,
)

@Entity
// ...
class BookAuthor(
    @Id
    @Column(name = "author_id")
    val authorId: Long,
) {
    @Id
    @ManyToOne
    @JoinColumn(name = "isbn")
    lateinit var book: Book
}

@Entity
// ...
class Author(
    @Id
    @Column(name = "author_id")
    val authorId: Long,

    // ...
)

from(
    entity(Book::class),
    join(Book::authors), // Association Join
    join(Author::class).on(path(BookAuthor::authorId).eq(path(Author::authorId))), // Join
)
```

Call `as()` after `join()` to alias the entity being joined.
This can be useful if you use multiple entities with the same type in a from clause.

```kotlin
from(
    entity(Book::class),
    join(Book::authors).`as`(entity(BookAuthor::class, "author")),
)
```

### Where clause

Use `where()` and pass [Predicate](predicates.md) to restrict the data when building a where clause in the select statement.
You can use `whereAnd()` as a short form of `where()` and `and()`.
You can also use `whereOr()` as a short form of `where()` and `or()`.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

### Group by clause

Use `groupBy()` and pass [Expression](expressions.md) to create unique groups of data when building a group by clause in the select statement.

```kotlin
groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

Use `having()` and pass [Expression](expressions.md) to further restrict the data when building a having clause in the select statement.
You can use `havingAnd()` as a short form of `having()` and `and()`.
You can also use `havingOr()` as a short form of `having()` and `or()`.

```kotlin
having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

Use `orderBy()` and pass [Sort](sorts.md) to return data in the declared order when building an order by clause in the select statement.

```kotlin
orderBy(
    path(Book::isbn).asc(),
)
```

## Update statement

Call `update()` in `jpql()` to build an update statement.

```kotlin
val query = jpql {
    update(
        entity(Book::class)
    ).set(
        path(Book::price)(BookPrice::value),
        BigDecimal.valueOf(100)
    ).set(
        path(Book::salePrice)(BookPrice::value),
        BigDecimal.valueOf(80)
    ).where(
        path(Book::isbn).eq(Isbn("01"))
    )
}
```

### Update clause

Use `update()` and pass [Entity](entities.md) to specify the entity to modify when building an update clause in the update statement.

```kotlin
update(
    entity(Employee::class),
)
```

### Set clause

Use `set()` and pass [Expression](expressions.md) to assign values when building a set clause in the update statement.
You can use multiple assignments by adding `set()` after `set()`.

```kotlin
set(
    path(Book::price)(BookPrice::value),
    BigDecimal.valueOf(100)
).set(
    path(Book::salePrice)(BookPrice::value),
    BigDecimal.valueOf(80)
)
```

### Where clause

Use `where()` and pass [Predicate](predicates.md) to restrict the data when building a where clause in the update statement.
You can use `whereAnd()` as a short form of `where()` and `and()`.
You can also use `whereOr()` as a short form of `where()` and `or()`.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

## Delete statement

Call `deleteFrom()` in `jpql()` to build a delete statement.

```kotlin
val query = jpql {
    deleteFrom(
        entity(Book::class),
    ).where(
        path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
    )
}
```

### Delete from clause

Use `deleteFrom()` and pass [Entity](entities.md) to specify the entity to delete when building a delete from clause in the delete statement.

```kotlin
deleteFrom(
    entity(Book::class),
)
```

### Where clause

Use `where()` and pass [Predicate](predicates.md) to restrict the data when building a where clause in the delete statement.
You can use `whereAnd()` as a short form of `where()` and `and()`.
You can also use `whereOr()` as a short form of `where()` and `or()`.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

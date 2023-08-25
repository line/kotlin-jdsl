# Statements

The JPQL supports select, update, and delete statements. Kotlin JDSL provides a DSL to build them.

## Select statement

Calling `select()` in `jpql()` allows you to build a select statement.

```kotlin
val query = jpql {
    select<Row>(
        path(Employee::employeeId).`as`(expression("employeeId")),
        count(Employee::employeeId).`as`(expression("count")),
    ).from(
        entity(Employee::class),
        join(Employee::departments),
    ).where(
        path(EmployeeDepartment::departmentId).eq(3L)
    ).groupBy(
        path(Employee::employeeId),
    ).having(
        count(Employee::employeeId).greaterThan(1L),
    ).orderBy(
        expression<Long>("employeeId").asc()
    )
}
```

### Select clause

To build a select clause in the select statement, you can use `select()` and pass [`Expression`](expressions.md) to project.
If you pass only one `Expression` to `select()`, it will infer a return type from `Expression`.
However, if you pass more than one `Expression`, it cannot infer the type, so you need to specify the type.

```kotlin
// It can infer the result type.
select(path(Author::authorId))

// It cannot infer the result type.
select(path(Author::authorId), path(Author::name))

// This allows it to know the result type.
select<CustomEntity>(path(Author::authorId), path(Author::name))
```

#### DTO projection

Specifying a DTO class and passing parameters of the constructor to `selectNew()` allows you to build a DTO projection.

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

To build a from clause in the select statement, you can use `from()` and pass [Entity](entities.md) and [Join](statements.md#join) to specify the entities to select from.

```kotlin
from(
    entity(Author::class),
    join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
)
```

#### Join

To combining the entities to select from, you can use `join()` and `fetchJoin()`.
There are two types of `join()`: Join and Association Join.
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

Calling `as()` after `join()` allows you to alias the entity being joined.
This can be useful if you use multiple entities with the same type in a from clause.

```kotlin
from(
    entity(Book::class),
    join(Book::authors).`as`(entity(BookAuthor::class, "author")),
)
```

### Where clause

To build a where clause in the select statement, you can use `where()` and pass [Predicate](predicates.md) to restrict the data.
You can use `whereAnd()` as a shortened form of `where()` and `and()`.
You can also use `whereOr()` as a shortened form of `where()` and `or()`.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

### Group by clause

To build a group by clause in the select statement, you can use `groupBy()` and pass [Expression](expressions.md) to create unique groups of data.

```kotlin
groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

To build a having clause in the select statement, you can use `having()` and pass [Expression](expressions.md) to further restrict the data.
You can use `havingAnd()` as a shortened form of `having()` and `and()`.
You can also use `havingOr()` as a shortened form of `having()` and `or()`.

```kotlin
having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

To build an order by clause in the select statement, you can use `orderBy()` and pass [Sort](sorts.md) to return data in the declared order.

```kotlin
orderBy(
    path(Book::isbn).asc(),
)
```

## Update statement

Calling `update()` in `jpql()` allows you to build an update statement.

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

To build an update clause in the update statement, you can use `update()` and pass [Entity](entities.md) to specify the entity to modify.

```kotlin
update(
    entity(Employee::class),
)
```

### Set clause

To build a set clause in the update statement, you can use `set()` and pass [Expression](expressions.md) to assign values.
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

To build a where clause in the update statement, you can use `where()` and pass [Predicate](predicates.md) to restrict the data.
You can use `whereAnd()` as a shortened form of `where()` and `and()`.
You can also use `whereOr()` as a shortened form of `where()` and `or()`.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

## Delete statement

Calling `deleteFrom()` in `jpql()` allows you to build a delete statement.

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

To build a delete from clause in the delete statement, you can use `deleteFrom()` and pass [Entity](entities.md) to specify the entity to delete.

```kotlin
deleteFrom(
    entity(Book::class),
)
```

### Where clause

To build a where clause in the delete statement, you can use `where()` and pass [Predicate](predicates.md) to restrict the data.
You can use `whereAnd()` as a shortened form of `where()` and `and()`.
You can also use `whereOr()` as a shortened form of `where()` and `or()`.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

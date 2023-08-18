# Statements

With Kotlin JDSL, you can create select, update, and delete statements.

## Select statement

By using the select function within a block of a jpql function, you can create a select statement. Each clause in the select statement can be represented by a function corresponding to the name of the clause.

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

A select clause can be represented using select function. Select function takes [expression](expressions.md)[s](expressions.md) as parameters. If you pass only one expression to select function, Kotlin JDSL will infer the type from the expression. However, if you pass multiple expressions, the Kotlin JDSL cannot infer the type, so you need to specify the result type for select.

```kotlin
// It can infer the result type.
select(path(Author::authorId))

// It cannot infer the result type.
select(path(Author::authorId), path(Author::name)) 

// This allows it to know the result type.
select<CustomEntity>(path(Author::authorId), path(Author::name)) 
```

DTO projection can be represented using selectNew function. By specifying the DTO class you want to project in the selectNew function and passing the parameters of the class as parameters, you represent the DTO projection using JPQL's NEW keyword.

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

A from clause can be represented using from function. From function takes [entities](entities.md) and [joins](statements.md#join) as parameters. The first parameter of the from function can take only an entity.

<pre class="language-kotlin"><code class="lang-kotlin"><strong>from(
</strong>    entity(Author::class),
    join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
)
</code></pre>

### Join

A join clause can be represented using join or fetchJoin functions. There are two types of joins: Join and Association join. Join represents a join between two unrelated entities, and the on condition is always required. Association join represents a join between two related entities, and the on condition is optional.

```kotlin
from(
    entity(Book::class),
    join(Book::authors),
    join(Author::class).on(path(BookAuthor::authorId).eq(path(Author::authorId))),
)
```

By using as function after the join function, you can use the aliased entity in other clauses of the select statement. If you want to include more than one entity of the same type in the from clause, you can use this.

```kotlin
from(
    entity(Book::class),
    join(Book::authors).`as`(entity(BookAuthor::class, "author")),
)
```

### Where clause

A where clause can be represented using where function. Where function takes [predicates](predicates.md) as parameters. You can use the whereAnd and whereOr functions to combine predicates into AND or OR.

{% hint style="info" %}
If the all predicates in whereAnd function are all null or empty, this will print 1 = 1. For the whereOr function, this will print 1 = 0.
{% endhint %}

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

WhereAnd and whereOr functions ignore the passed parameter if it is null. You can use this feature to create readable dynamic queries.

```kotlin
data class BookSpec(
    val isbn: Isbn?,
    val publisherId: Long?,
    val authorId: Long?,
)

val query = jpql {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
        join(Book::publisher),
        join(Book::authors),
    ).whereAnd(
        spec.isbn?.let { path(Book::isbn).eq(it) },
        spec.publisherId?.let { path(BookPublisher::publisherId).eq(it) },
        spec.authorId?.let { path(BookAuthor::authorId).eq(it) },
    )
}
```

### Group by clause

A group by clause can be represented using groupBy function. GroupBy function takes [expressions](expressions.md) as parameters.&#x20;

```kotlin
groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

A having clause can be represented using having function. Having function takes [predicates](predicates.md) as parameters. You can use the havingAnd and havingOr functions to combine predicates into AND or OR.

{% hint style="info" %}
If the all predicates in havingAnd function are all null or empty, this will print 1 = 1. For the havingOr function, this will print 1 = 0.
{% endhint %}

```kotlin
having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

A order by clause can be represented using orderBy function. OrderBy function takes [sorts](sorts.md) as parameters.&#x20;

```kotlin
orderBy(
    path(Book::isbn).asc(),
)
```

## Update statement

You can create a update statement by calling the `update` function inside the `jpql` function. The `update` function starts the update statement DSL, and the method chain allows you to see what types of functions are available and call them.

```kotlin
update(
    Employee::class,
).set(
    path(Employee::nickname),
    path(Employee::name),
).set(
    // ...
)
```

### Update clause

You can represent a `update` clause by passing the `Entity` you want to update in the `update` function.&#x20;

```kotlin
update(
    Employee::class,
)
```

### Set clause

You can represent a `set` clause by passing the pair of the `Expressions` you want to assign in the `set` function. The `set` function can be called multiple times before calling the `where` function.

```kotlin
update(
    Employee::class,
).set(
    path(Employee::nickname),
    path(Employee::name),
).set(
    // ...
)
```

### Where clause

You can represent a `where` clause by listing the predicates you want to filter in the `where` function. If null is passed as a parameter, it will be ignored. This allows you to dynamically create a `where` clause.

{% hint style="info" %}
If the list passed through the `whereAnd` function is empty or all null, this will print 1 = 1. For the `whereOr` function, this will print 1 = 0.
{% endhint %}

```kotlin
update(
    Employee::class,
).set(
    path(Employee::nickname),
    path(Employee::name),
).where(
    path(Employee::nickname).isNull(),
)
```

## Delete statement

You can create a delete statement by calling the `delete` function inside the `jpql` function. The `delete` function starts the delete statement DSL, and the method chain allows you to see what types of functions are available and call them.

```kotlin
deleteFrom(
    Book::class,
).where(
    path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
)
```

### Delete from clause

You can represent a `delete from` clause by passing the `Entity` you want to delete in the `deleteFrom` function.&#x20;

```kotlin
deleteFrom(
    entity(Book::class),
)
```

### Where clause

You can represent a `where` clause by listing the predicates you want to filter in the `where` function. If null is passed as a parameter, it will be ignored. This allows you to dynamically create a `where` clause.

{% hint style="info" %}
If the list passed through the `whereAnd` function is empty or all null, this will print 1 = 1. For the `whereOr` function, this will print 1 = 0.
{% endhint %}

```kotlin
deleteFrom(
    Book::class,
).where(
    path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
)
```

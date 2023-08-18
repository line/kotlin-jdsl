# Statements

With Kotlin JDSL, you can create select, update, and delete statements.

## Select statement

By using select function within a block of jpql function, you can create a select statement. Each clause in the select statement can be represented by a function corresponding to the name of the clause.

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

A select clause can be represented by select function. Select function takes [expressions](expressions.md) as parameters. If you pass only one expression to select function, Kotlin JDSL will infer the type from the expression. However, if you pass multiple expressions, the Kotlin JDSL cannot infer the type, so you need to specify the result type for select.

```kotlin
// It can infer the result type.
select(path(Author::authorId))

// It cannot infer the result type.
select(path(Author::authorId), path(Author::name)) 

// This allows it to know the result type.
select<CustomEntity>(path(Author::authorId), path(Author::name)) 
```

DTO projection can be represented by selectNew function. By specifying the DTO class you want to project in the selectNew function and passing the parameters of the class as parameters, you represent the DTO projection using JPQL's NEW keyword.

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

A from clause can be represented by from function. From function takes [entities](entities.md) and [joins](statements.md#join) as parameters. The first parameter of the from function can take only an entity.

<pre class="language-kotlin"><code class="lang-kotlin"><strong>from(
</strong>    entity(Author::class),
    join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
)
</code></pre>

### Join

A join clause can be represented by join or fetchJoin functions. There are two types of joins: Join and Association join. Join represents a join between two unrelated entities, and the on condition is always required. Association join represents a join between two related entities, and the on condition is optional.

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

A where clause can be represented by where function. Where function takes [predicates](predicates.md) as parameters. You can use the whereAnd and whereOr functions to combine predicates into AND or OR.

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

A group by clause can be represented by groupBy function. GroupBy function takes [expressions](expressions.md) as parameters.&#x20;

```kotlin
groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

A having clause can be represented by having function. Having function takes [predicates](predicates.md) as parameters. You can use the havingAnd and havingOr functions to combine predicates into AND or OR.

{% hint style="info" %}
If the all predicates in havingAnd function are all null or empty, this will print 1 = 1. For the havingOr function, this will print 1 = 0.
{% endhint %}

```kotlin
having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

A order by clause can be represented by orderBy function. OrderBy function takes [sorts](sorts.md) as parameters.&#x20;

```kotlin
orderBy(
    path(Book::isbn).asc(),
)
```

## Update statement

By using update function within a block of jpql function, you can create a update statement. Each clause in the update statement can be represented by a function corresponding to the name of the clause.

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

An update clause can be represented by update function. Update function takes an [entity](entities.md) as a parameter.

```kotlin
update(
    entity(Employee::class),
)
```

### Set clause

A set clause can be represented by set function. Set function takes [expressions](expressions.md) as parameters.  The first parameter of set function is the target to be assigned, and the second parameter is the value to assign. You can represent assignments to multiple columns by using set function after set function.

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

A where clause can be represented by where function. Where function takes [predicates](predicates.md) as parameters. You can use the whereAnd and whereOr functions to combine predicates into AND or OR.

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

<pre class="language-kotlin"><code class="lang-kotlin">data class BookSpec(
    val isbn: Isbn?,
    val publishDate: OffsetDateTime?,
)

val query = jpql {
    update(
        entity(Book::class)
    ).set(
        path(Book::price)(BookPrice::value),
        BigDecimal.valueOf(100)
    ).set(
        path(Book::salePrice)(BookPrice::value),
        BigDecimal.valueOf(80)
<strong>    ).whereAnd(
</strong>        spec.isbn?.let { path(Book::isbn).eq(it) },
        spec.publishDate?.let { path(Book::publishDate).eq(it) },
    )
}
</code></pre>

## Delete statement

By using deleteFrom function within a block of jpql function, you can create a delete statement. Each clause in the delete statement can be represented by a function corresponding to the name of the clause.

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

An delete from clause can be represented by deleteFrom function. DeleteFrom function takes an [entity](entities.md) as a parameter.

```kotlin
deleteFrom(
    entity(Book::class),
)
```

### Where clause

A where clause can be represented by where function. Where function takes [predicates](predicates.md) as parameters. You can use the whereAnd and whereOr functions to combine predicates into AND or OR.

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
    val publishDate: OffsetDateTime?,
)

val query = jpql {
    deleteFrom(
        entity(Book::class),
    ).whereAnd(
        spec.isbn?.let { path(Book::isbn).eq(it) },
        spec.publishDate?.let { path(Book::publishDate).eq(it) },
    )
}
```

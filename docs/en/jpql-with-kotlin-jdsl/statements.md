# Statements

Kotlin JDSL supports the 3 statements: select, update, and delete. Each statement can be created using the Fluent-style DSL provided by the Kotlin JDSL.

## Select statement

You can create a select statement by calling the `select` function inside the `jpql` function. The `select` function starts the select statement DSL, and the method chain allows you to see what types of functions are available and call them.

```kotlin
select(
    path(Author::authorId),
).from(
    entity(Author::class),
)
```

### Select clause

You can represent a `select` clause by listing the expressions you want to look up in the `select` function. If you pass only one expression to the select function, it will automatically infer the type of the result of the projection. However, if you pass more than one expression, it will not be able to infer the type of the result of the projection, so you need to specify the type.

```kotlin
select(path(Author::authorId)) // It can infer the result type.

select(path(Author::authorId), path(Author::name)) // It can't infer the result type.

select<CustomEntity>(path(Author::authorId), path(Author::name)) // This allows it to know the result type.
```

#### DTO projection

You can use DTO projection by passing the DTO class you want to project and the expression in the order of the constructor parameters of DTO class to select new function.

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

You can represent a `from` clause by listing the froms you want to join in the `from` function. If null is passed as a parameter, it will be ignored. This allows you to dynamically create a `from` clause.

<pre class="language-kotlin"><code class="lang-kotlin"><strong>select(
</strong>    path(Author::name),
).from(
    entity(Author::class),
    isbn?.let { join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))) }, 
).whereAnd(
    isbn?.let { path(BookAuthor::isbn).equal(isbn) }, 
)
</code></pre>

### Where clause

You can represent a `where` clause by listing the predicates you want to filter in the `where` function. If null is passed as a parameter, it will be ignored. This allows you to dynamically create a `where` clause.

{% hint style="info" %}
If the list passed through the `whereAnd` function is empty or all null, this will print 1 = 1. For the `whereOr` function, this will print 1 = 0.
{% endhint %}

```kotlin
select(
    path(Book::isbn),
).from(
    entity(Book::class),
).where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
).orderBy(
    path(Book::isbn).asc(),
)
```

### Group by clause

You can represent a `group by` clause by listing the expressions you want to group in the `group by` function. If null is passed as a parameter, it will be ignored. This allows you to dynamically create a `group by` clause.

```kotlin
data class Row(
    val departmentId: Long,
    val count: Long,
)

selectNew<Row>(
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
).from(
    entity(Employee::class),
    join(Employee::departments),
).groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

You can represent a `having` clause by listing the predicates you want to filter in the `having` function. If null is passed as a parameter, it will be ignored. This allows you to dynamically create a `having` clause.

{% hint style="info" %}
If the list passed through the `havingAnd` function is empty or all null, this will print 1 = 1. For the `havingOr` function, this will print 1 = 0.
{% endhint %}

```kotlin
data class Row(
    val employeeId: Long,
    val count: Long,
)

select<Row>(
    path(Employee::employeeId).`as`(expression("employeeId")),
    count(Employee::employeeId).`as`(expression("count")),
).from(
    entity(Employee::class),
    join(Employee::departments),
).groupBy(
    path(Employee::employeeId),
).having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

You can represent a `order by` clause by listing the sorts you want to sort in the `order by` function. If null is passed as a parameter, it will be ignored. This allows you to dynamically create a `having` clause.

```kotlin
select(
    path(Book::isbn),
).from(
    entity(Book::class),
).orderBy(
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

# How to sort by DTO fields or aliases?

You can sort the results of a query by a field in a DTO projection or by an alias you have defined in the `select` clause.

To do this, you first need to create an alias for the expression in the `select` clause. Then, you can refer to this alias in the `orderBy` clause.

Here is an example of sorting by an aliased column, which is projected into a DTO:

```kotlin
data class BookInfo(
    val name: String,
    val authorCount: Long
)

// 1. Define an alias for the expression.
val authorCountAlias = expression(Long::class, "authorCount")

val query = jpql {
    selectNew<BookInfo>(
        path(Book::name),
        count(Book::authors).`as`(authorCountAlias) // 2. Use the alias in the select clause.
    ).from(
        entity(Book::class)
    ).groupBy(
        path(Book::name)
    ).orderBy(
        authorCountAlias.asc() // 3. Use the alias in the orderBy clause.
    )
}

val bookInfos = entityManager.createQuery(query, context).resultList
```

In this example:
1.  We create an `Expression` to serve as an alias named `authorCountAlias`.
2.  In the `selectNew` clause, we use `as(authorCountAlias)` to associate the `count(Book::authors)` expression with our alias.
3.  In the `orderBy` clause, we can now refer to `authorCountAlias` to sort the results.

This pattern allows you to sort by any computed value or aggregate function that you include in your DTO.
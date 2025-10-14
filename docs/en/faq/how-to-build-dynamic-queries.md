# How to build dynamic queries?

You can build dynamic queries by adding predicates to the `where` clause based on conditions.
Kotlin JDSL's `where` clause can take a list of predicates. The `and()` and `or()` functions are useful for this.

A common pattern is to create a mutable list of `Predicate`s and add conditions to it. Then, you can pass this list to the `where` clause using `and()`.

If the list of predicates passed to `and()` is empty, it is treated as `1 = 1`, which means no filtering is applied. This is useful for dynamic queries where all conditions might be optional.

{% hint style="warning" %}
Be cautious when building dynamic queries. If no conditions are applied, the query will select all rows from the entity, which could lead to performance issues with large tables. Always consider the case where no filters are active.
{% endhint %}

Here is an example:

```kotlin
fun findBooks(title: String?, authorName: String?): List<Book> {
    val query = jpql {
        select(
            entity(Book::class)
        ).from(
            entity(Book::class)
        ).where(
            and(
                title?.let { path(Book::title).like("%$it%") },
                authorName?.let { path(Book::author)(Author::name).like("%$it%") },
            )
        )
    }
    return entityManager.createQuery(query, context).resultList
}
```

In the example above, if `title` is not null, a `like` predicate is created. The same applies to `authorName`. The `and()` function will filter out any `null` predicates that result from the `let` blocks when the parameters are null. If both `title` and `authorName` are `null`, the `where` clause will be effectively empty, and all books will be returned.

Alternatively, you can build a list of predicates:

```kotlin
fun findBooks(title: String?, authorName: String?): List<Book> {
    val query = jpql {
        select(
            entity(Book::class)
        ).from(
            entity(Book::class)
        ).whereAnd(
            mutableListOf<Predicate?>().apply {
                if (!title.isNullOrBlank()) {
                    add(path(Book::title).like("%$title%"))
                }
                if (!authorName.isNullOrBlank()) {
                    add(path(Book::author)(Author::name).like("%$authorName%"))
                }
            }
        )
    }
    return entityManager.createQuery(query, context).resultList
}
```

The `whereAnd` is a shorthand for `where(and(...))`. If the list is empty, it will not add any conditions to the query.

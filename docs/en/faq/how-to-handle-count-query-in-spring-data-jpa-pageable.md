# How to handle count query in Spring Data JPA Pageable?

When using `findPage` from `KotlinJdslJpqlExecutor` with Spring Data JPA's `Pageable`, you might encounter issues with the automatically generated count query, especially with complex queries involving `join` and `groupBy`.

Spring Data JPA, by default, tries to generate a count query from your main query. However, this generated query is often incorrect for complex scenarios.

To solve this, you can provide a separate count query. `KotlinJdslJpqlExecutor` has a `findPage` method that accepts a separate count query lambda.

Here is an example:

```kotlin
interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor

val page: Page<Book> = bookRepository.findPage(pageable,
    { // Query
        select(
            entity(Book::class)
        ).from(
            entity(Book::class),
            join(Book::author)
        ).where(
            // ... some conditions
        ).groupBy(
            path(Book::isbn)
        )
    },
    { // Count Query
        select(
            countDistinct(path(Book::isbn))
        ).from(
            entity(Book::class),
            join(Book::author)
        ).where(
            // ... same conditions
        )
    }
)
```

By providing a separate, simplified count query, you can avoid the issues with the automatically generated one. The count query should return a single `Long` value. It's important to apply the same `where` conditions to the count query as you do to the main query to get the correct total count.

Note that when you have a `groupBy` clause in your main query, the count query should usually count the distinct values of the grouped expression, as shown in the example with `countDistinct(path(Book::isbn))`.

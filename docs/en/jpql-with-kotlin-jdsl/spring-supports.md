# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL supports Spring Boot AutoConfigure.
If you have Spring Boot and `com.linecorp.kotlin-jdsl:spring-data-jpa-support` or `com.linecorp.kotlin-jdsl:spring-batch-support` dependency together, the `JpqlRenderContext` bean is created by AutoConfiguration.

If you declare your `JpqlSerializer` as a bean, it will be included with the `JpqlRenderContext` bean.

## Spring Data Repository

If your `JpaRepository` extends `KotlinJdslJpqlExecutor`, you can use the extension provided by Kotlin JDSL.

```kotlin
interface AuthorRepository : JpaRepository<Author, Long>, KotlinJdslJpqlExecutor
interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor

authorRepository.findAll {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class),
        join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
    ).groupBy(
        path(Author::authorId),
    ).orderBy(
        count(Author::authorId).desc(),
    )
}

bookRepository.findPage(pageable) {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    )
}
```

## Spring Batch

Spring Batch provides `JpaPagingItemReader` and `JpaCursorItemReader` for querying data with JPQL.
Kotlin JDSL provides `KotlinJdslQueryProvider` so that a JPQL query created in DSL can be executed on it.

```kotlin
@Auwoired
lateinit var queryProviderFactory: KotlinJdslQueryProviderFactory

val queryProvider = queryProviderFactory.create {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class),
    )
}

JpaCursorItemReaderBuilder<Isbn>()
    .entityManagerFactory(entityManagerFactory)
    .queryProvider(queryProvider)
    .saveState(false)
    .build()
```

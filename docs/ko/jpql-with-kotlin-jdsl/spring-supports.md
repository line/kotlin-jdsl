# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL은 Spring Boot AutoConfigure를 지원합니다.
만약 프로젝트가 Spring Boot와 `com.linecorp.kotlin-jdsl:spring-data-jpa-support` dependency를 같이 포함하고 있다면, `JpqlRenderContext` bean이 `KotlinJdslAutoConfiguration`를 통해 자동 생성 됩니다.

만약 `JpqlSerializer`를 bean으로 선언했다면, 자동으로 `JpqlRenderContext`에 해당 bean이 포함됩니다.

## Spring Data Repository

만약 사용하고 있는 `JpaRepository`가 `KotlinJdslJpqlExecutor`를 상속하면, Kotlin JDSL이 제공하는 확장 기능을 사용할 수 있습니다.

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

SpringBatch는 JPQL로 쿼리를 할 수 있도록 `JpaPagingItemReader`와 `JpaCursorItemReader`를 제공합니다.
Kotlin JDSL은 DSL로 생성된 JPQL 쿼리가 JpqReader들에서 실행될 수 있도록 `KotlinJdslQueryProvider`을 제공합니다.

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

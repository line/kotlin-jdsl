# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL supports Spring Boot AutoConfigure.
If you have Spring Boot and `com.linecorp.kotlinjdsl:spring-data-jpa-support` dependency together, the `JpqlRenderContext` bean is created by `KotlinJdslAutoConfiguration`.

If you declare your `JpqlSerializer` as a bean, it will be included with the `JpqlRenderContext` bean.

## Spring Data Repository

If your `JpaRepository` extends `KotlinJdslJpqlExecutor`, you can use the extension provided by Kotlin JDSL.

```kotlin
interface AuthorRepository : JpaRepository<Author, Long>, KotlinJdslJpqlExecutor
interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor

authorRepository.findFirst {
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

bookRepository.findAll(pageable) {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    )
}
```

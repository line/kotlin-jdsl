# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL supports Spring Boot AutoConfigure. If you have Spring Boot and `com.linecorp.kotlinjdsl:spring-data-jpa-support` dependency together, the `JpqlRenderContext` bean is automatically created by `KotlinJdslAutoConfiguration`.

If you register your `JpqlSerializer` as a bean, it will be included with the `JpqlRenderContext` bean.

## Spring Data Repository

Kotlin JDSL supports extensions from the Spring Data Jpa Repository. If you inherit `KotlinJdslJpqlExecutor` for your repository interface, `KotlinJdslAutoConfiguration` allows you to use the Spring Data Repository extensions provided by Kotlin JDSL.

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

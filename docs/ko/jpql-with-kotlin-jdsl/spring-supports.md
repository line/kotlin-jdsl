# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL은 Spring Boot AutoConfigure를 지원합니다.
만약 프로젝트가 Spring Boot와 `com.linecorp.kotlinjdsl:spring-data-jpa-support` dependency를 같이 포함하고 있다면, `JpqlRenderContext` bean이 `KotlinJdslAutoConfiguration`를 통해 자동 생성 됩니다.

만약 `JpqlSerializer`를 bean으로 선언했다면, 자동으로 `JpqlRenderContext`에 해당 bean이 포함됩니다.

## Spring Data Repository

만약 사용하고 있는 `JpaRepository`가 `KotlinJdslJpqlExecutor`를 상속하면, Kotlin JDSL이 제공하는 확장 기능을 사용할 수 있습니다.

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

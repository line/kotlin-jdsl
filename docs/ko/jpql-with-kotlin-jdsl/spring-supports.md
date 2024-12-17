# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL은 Spring Boot AutoConfigure를 지원합니다.
만약 프로젝트가 Spring Boot와 `com.linecorp.kotlin-jdsl:spring-data-jpa-support` dependency를 같이 포함하고 있다면, `JpqlRenderContext` bean이 `KotlinJdslAutoConfiguration`을 통해 자동 생성 됩니다.

만약 `JpqlSerializer` 또는 `JpqlIntrospector`를 bean으로 선언했다면, 자동으로 `JpqlRenderContext`에 해당 bean이 포함됩니다.

## Spring Data Repository

만약 사용하고 있는 `JpaRepository`가 `KotlinJdslJpqlExecutor`를 상속하면, Kotlin JDSL이 제공하는 확장 기능을 사용할 수 있습니다.

```kotlin
interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor

val result: List<Isbn?> = bookRepository.findAll {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    )
}

val result: Page<Isbn?> = bookRepository.findPage(pageable) {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    )
}

val result: Slice<Isbn?> = bookRepository.findSlice(pageable) {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    )
}

val result: Stream<Isbn?> = bookRepository.findStream {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    )
}
```

{% hint style="info" %}
만약 `KotlinJdslJpqlExecutor`를 `@DataJpaTest`에서 사용하고 싶다면 `KotlinJdslAutoConfiguration`를 테스트에서 직접 import 해야 합니다.
`@DataJpaTest`는 slice test이기 때문에 최소한의 bean만 생성합니다. 그리고 이 bean에는 `KotlinJdslAutoConfiguration`이 포함되어 있지 않습니다.
그래서 `@DataJpaTest`에서 Kotlin JDSL의 기능을 사용하고 싶다면 테스트에서 `KotlinJdslAutoConfiguration`를 직접 import 해야 합니다.
{% endhint %}

## Spring Batch

SpringBatch는 JPQL로 쿼리를 할 수 있도록 `JpaPagingItemReader`와 `JpaCursorItemReader`를 제공합니다.
Kotlin JDSL은 DSL로 생성된 JPQL 쿼리가 위 ItemReader들에서 실행될 수 있도록 `KotlinJdslQueryProvider`를 제공합니다.

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

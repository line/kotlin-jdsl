# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL supports Spring Boot AutoConfigure.
If your project has Spring Boot and one of the following dependencies, AutoConfiguration automatically creates the `JpqlRenderContext` bean.

* `com.linecorp.kotlin-jdsl:spring-data-jpa-support` (for Spring Boot 2 or 3)
* `com.linecorp.kotlin-jdsl:spring-data-jpa-boot4-support` (for Spring Boot 4)
* `com.linecorp.kotlin-jdsl:spring-batch-support`

If you declare your `JpqlSerializer` or `JpqlIntrospector` as a bean, it will be included with the `JpqlRenderContext` bean.

#### EntityManager bean registration for Spring Boot 4

In Spring Boot 4 (Spring Data JPA 4), `EntityManagerBeanDefinitionRegistrarPostProcessor` has been deprecated, so the `EntityManager` bean is no longer auto-registered.
According to the [Spring Data 2025.1 Release Notes](https://github.com/spring-projects/spring-data-commons/wiki/Spring-Data-2025.1-Release-Notes#reuse-of-shared-entitymanager), when a shared `EntityManager` is required, using `SharedEntityManagerCreator` directly in your configuration is the recommended approach.
Since `KotlinJdslJpqlExecutor` requires an `EntityManager`, Spring Boot 4 users must register the `EntityManager` bean manually as follows:

```kotlin
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.orm.jpa.SharedEntityManagerCreator

@SpringBootApplication
class Application {
    @Bean
    fun entityManager(entityManagerFactory: EntityManagerFactory): EntityManager =
        SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory)
}
```

Kotlin JDSL respects Spring Boot 4's design decision and does not forcibly add the `EntityManager` bean in AutoConfiguration.

## Spring Data Repository

If your `JpaRepository` extends `KotlinJdslJpqlExecutor`, you can use the extension provided by Kotlin JDSL.

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
If you want to use `KotlinJdslJpqlExecutor` in `@DataJpaTest`, you need to import `KotlinJdslAutoConfiguration` in the test.
Since `@DataJpaTest` is a slice test, it only creates minimal beans.
And the minimal bean does not include `KotlinJdslAutoConfiguration`.
So if you want to use the features of Kotlin JDSL in `@DataJpaTest`, you need to import `KotlinJdslAutoConfiguration` directly in your test.
{% endhint %}

## Spring Batch

Spring Batch provides `JpaPagingItemReader` and `JpaCursorItemReader` for querying data with JPQL.
Kotlin JDSL provides `KotlinJdslQueryProvider` so that a JPQL query created in DSL can be executed in it.

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

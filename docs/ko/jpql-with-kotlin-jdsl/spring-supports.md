# Spring supports

## Spring Boot AutoConfigure

Kotlin JDSL은 Spring Boot AutoConfigure를 지원합니다.
만약 프로젝트가 Spring Boot와 아래의 dependency 중 하나를 포함하고 있다면, `JpqlRenderContext` bean이 `KotlinJdslAutoConfiguration`을 통해 자동 생성 됩니다.

* `com.linecorp.kotlin-jdsl:spring-data-jpa-support` (Spring Boot 2 또는 3 사용 시)
* `com.linecorp.kotlin-jdsl:spring-data-jpa-boot4-support` (Spring Boot 4 사용 시)
* `com.linecorp.kotlin-jdsl:spring-batch-support`

만약 `JpqlSerializer` 또는 `JpqlIntrospector`를 bean으로 선언했다면, 자동으로 `JpqlRenderContext`에 해당 bean이 포함됩니다.

#### Spring Boot 4에서 EntityManager bean 등록

Spring Boot 4 (Spring Data JPA 4)에서는 `EntityManagerBeanDefinitionRegistrarPostProcessor`가 deprecated되어 `EntityManager` bean이 더 이상 자동 등록되지 않습니다.
[Spring Data 2025.1 Release Notes](https://github.com/spring-projects/spring-data-commons/wiki/Spring-Data-2025.1-Release-Notes#reuse-of-shared-entitymanager)에 따르면, shared `EntityManager`가 필요한 경우 `SharedEntityManagerCreator`를 설정에서 직접 사용하는 것이 권장됩니다.
`KotlinJdslJpqlExecutor`는 `EntityManager`를 필요로 하므로, Spring Boot 4 사용 시 아래와 같이 `EntityManager` bean을 수동으로 등록해야 합니다.

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

Kotlin JDSL은 Spring Boot 4의 설계 변경을 존중하여 `EntityManager` bean을 AutoConfiguration에서 강제로 추가하지 않습니다.

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

# Spring Data Kotlin JDSL

Integrates Kotlin JDSL with Spring Data JPA.

## Quick start

Add Spring Data Kotlin JDSL starter and Spring Boot Data JPA

```kotlin
dependencies {
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:x.y.z")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:x.y.z")
}
```

Inject SpringDataQueryFactory in your service and query using it

```kotlin
@Service
@Transactional
class Service(
    private val queryFactory: SpringDataQueryFactory,
) {
    fun findById(id: Long): Entity {
        return queryFactory.singleQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::id).equal(id))
        }
    }
}
```
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

kotlin-jdsl's spring boot starter is adopted hibernate as the default implementation of kotlin-jdsl, so to use eclipselink, you need to modify the build script as follows.

```kotlin
dependencies {
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:x.y.z") {
        exclude(group = "com.linecorp.kotlin-jdsl", module = "hibernate-kotlin-jdsl")
    }
    implementation("com.linecorp.kotlin-jdsl:eclipselink-kotlin-jdsl:x.y.z")
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:x.y.z")
}
```

Inject SpringDataQueryFactory in your service and query using it

```kotlin
@Service
@Transactional
class Service(
    private val queryFactory: SpringDataQueryFactory,
) {
    fun findById(id: Long): Book {
        return queryFactory.singleQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::id).equal(id))
        }
    }
}
```

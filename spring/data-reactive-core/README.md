# Spring Data Kotlin JDSL Reactive

Integrates Kotlin JDSL Reactive with Spring Data.

## Quick start - JPA 2.2

Add Spring Data Kotlin JDSL hibernate-reactive and Spring Data Commons

```kotlin
dependencies {
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive:x.y.z")
    implementation("org.springframework.data:spring-data-commons:x.y.z")
    implementation("org.hibernate.reactive:hibernate-reactive-core:x.y.z")
    implementation("io.smallrye.reactive:mutiny-kotlin:x.y.z")
}
```

## Quick start - JPA 3.0

Add Spring Data Kotlin JDSL hibernate-reactive and Spring Data Commons

```kotlin
dependencies {
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive-jakarta:x.y.z")
    implementation("org.springframework.data:spring-data-commons:x.y.z")
    implementation("org.hibernate.reactive:hibernate-reactive-core-jakarta:x.y.z")
    implementation("io.smallrye.reactive:mutiny-kotlin:x.y.z")
}
```
Unfortunately, kotlin-jdsl's data-reactive-core(-jakarta) does not support Spring's declarative Transaction and Autoconfigure, unlike data-core.  
The reason is that EntityManagerFactory must be created through ReactivePersistenceProvider, but Spring does not support this part.  
Also, since there is also no ReactiveTransactionManager, it is difficult to support declarative transactions.  
Therefore, we currently **only support integration with Spring Data Commons** (integrate with Page, Pageable, Range.Bound).

This part will be updated when support for Hibernate Reactive is added in Spring in the future.  
Therefore, you need to register EntityManagerFactory and SpringDataHibernateMutinyReactiveQueryFactory as beans by manually adding Configuration as shown below.

Create a Configuration object as follows.

```kotlin
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ReactiveSession::class)
class QueryCreatorConfiguration {
    @Bean
    @ConditionalOnMissingBean(SubqueryCreator::class)
    fun subqueryCreator(): SubqueryCreator {
        return SubqueryCreatorImpl()
    }

    @Bean
    fun entityManagerFactory() = Persistence.createEntityManagerFactory("persistenceUnitName")

    @Bean
    fun mutinySessionFactory(entityManagerFactory: EntityManagerFactory) =
        entityManagerFactory.unwrap(SessionFactory::class.java)

    @Bean
    fun queryFactory(sessionFactory: SessionFactory, subqueryCreator: SubqueryCreator): SpringDataHibernateMutinyReactiveQueryFactory {
        return SpringDataHibernateMutinyReactiveQueryFactory(
            sessionFactory = sessionFactory,
            subqueryCreator = subqueryCreator
        )
    }
}
```

If you really want full integration with Spring and Spring Boot, Spring Boot JPA,  
You need to create Hibernate Reactive versions of HibernateJpaVendorAdapter, HibernateJpaConfiguration, HibernateJpaAutoConfiguration, ReactiveTransactionManager, DataJpaTest.  
This is beyond our scope.

Inject SpringDataHibernateMutinyReactiveQueryFactory in your service and query using it.  
For more detailed usage, see [more](../../reactive-core/README.md#Quick-Start)

```kotlin
@Service
class Service(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
) {
    suspend fun findById(id: Long): Book {
        return queryFactory.singleQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::id).equal(id))
        }
    }
}
```
```

package com.linecorp.kotlinjdsl.test.reactive

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.EntityDsl
import javax.persistence.EntityManagerFactory

interface CriteriaQueryDslIntegrationTest<S> : EntityDsl, WithKotlinJdslAssertions {
    var factory: S
    var entityManagerFactory: EntityManagerFactory

    suspend fun persist(entity: Any)
    suspend fun persistAll(vararg entities: Any)
    suspend fun persistAll(entities: Collection<Any>)
    suspend fun removeAll(vararg entities: Any)
    suspend fun removeAll(entities: Collection<Any>)
    suspend fun <T> withFactory(block: suspend (ReactiveQueryFactory) -> T): T
}

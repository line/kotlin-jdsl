package com.linecorp.kotlinjdsl.test.integration

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.QueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.EntityDsl
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
abstract class AbstractCriteriaQueryDslIntegrationTest : EntityDsl, WithKotlinJdslAssertions {
    protected lateinit var entityManager: EntityManager

    protected val queryFactory: QueryFactory by lazy(LazyThreadSafetyMode.NONE) {
        QueryFactoryImpl(
            criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
            subqueryCreator = SubqueryCreatorImpl(),
        )
    }

    fun EntityManager.persistAll(vararg entities: Any) = persistAll(entities.toList())
    fun EntityManager.persistAll(entities: Collection<Any>) = entities.forEach { persist(it) }
    fun EntityManager.removeAll(vararg entities: Any) = removeAll(entities.toList())
    fun EntityManager.removeAll(entities: Collection<Any>) = entities.forEach {
        if (contains(it)) {
            remove(it)
        } else {
            remove(merge(it))
        }
    }

    fun EntityManager.flushAndClear() {
        flush(); clear()
    }
}

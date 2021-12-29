package com.linecorp.kotlinjdsl.test.integration

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.QueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.EntityDsl
import javax.persistence.EntityManager

abstract class AbstractCriteriaQueryDslIntegrationTest : EntityDsl(), WithKotlinJdslAssertions {
    protected abstract val entityManager: EntityManager

    protected val queryFactory: QueryFactory by lazy(LazyThreadSafetyMode.NONE) {
        QueryFactoryImpl(
            criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
            subqueryCreator = SubqueryCreatorImpl(),
        )
    }
}
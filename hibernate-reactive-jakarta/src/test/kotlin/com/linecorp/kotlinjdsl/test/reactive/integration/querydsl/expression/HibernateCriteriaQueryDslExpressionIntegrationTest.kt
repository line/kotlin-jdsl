package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.expression

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.expression.AbstractCriteriaQueryDslExpressionIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import jakarta.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslExpressionIntegrationTest :
    HibernateCriteriaIntegrationTest, AbstractCriteriaQueryDslExpressionIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

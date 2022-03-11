package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.expression

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.expression.AbstractCriteriaQueryDslExpressionIntegrationTest
import org.hibernate.reactive.stage.Stage
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslExpressionIntegrationTest :
    HibernateCriteriaIntegrationTest, AbstractCriteriaQueryDslExpressionIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

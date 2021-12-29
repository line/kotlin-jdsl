package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.expression

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.expression.AbstractCriteriaQueryDslExpressionIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class HibernateCriteriaQueryDslExpressionIntegrationTest : AbstractCriteriaQueryDslExpressionIntegrationTest() {
    override lateinit var entityManager: EntityManager
}
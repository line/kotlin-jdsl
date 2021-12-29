package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.orderby

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.orderby.AbstractCriteriaQueryDslOrderByIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class HibernateCriteriaQueryDslOrderByIntegrationTest : AbstractCriteriaQueryDslOrderByIntegrationTest() {
    override lateinit var entityManager: EntityManager
}
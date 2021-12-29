package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.having

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.having.AbstractCriteriaQueryDslHavingIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class HibernateCriteriaQueryDslHavingIntegrationTest : AbstractCriteriaQueryDslHavingIntegrationTest() {
    override lateinit var entityManager: EntityManager
}
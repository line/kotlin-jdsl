package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.limit

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.limit.AbstractCriteriaQueryDslLimitIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class HibernateCriteriaQueryDslLimitIntegrationTest : AbstractCriteriaQueryDslLimitIntegrationTest() {
    override lateinit var entityManager: EntityManager
}
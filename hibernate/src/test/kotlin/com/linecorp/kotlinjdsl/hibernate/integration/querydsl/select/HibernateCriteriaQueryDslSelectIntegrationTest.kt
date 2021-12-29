package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.select

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.select.AbstractCriteriaQueryDslSelectIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class HibernateCriteriaQueryDslSelectIntegrationTest : AbstractCriteriaQueryDslSelectIntegrationTest() {
    override lateinit var entityManager: EntityManager
}
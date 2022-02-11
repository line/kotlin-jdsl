package com.linecorp.kotlinjdsl.hibernate.criteriaquery

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.criteriaquery.AbstractCriteriaDeleteIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class HibernateCriteriaDeleteIntegrationTest : AbstractCriteriaDeleteIntegrationTest() {
    override lateinit var entityManager: EntityManager
}

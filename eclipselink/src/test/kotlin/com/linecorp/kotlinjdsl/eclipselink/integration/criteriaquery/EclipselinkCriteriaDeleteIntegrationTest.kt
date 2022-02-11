package com.linecorp.kotlinjdsl.eclipselink.integration.criteriaquery

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.criteriaquery.AbstractCriteriaDeleteIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class EclipselinkCriteriaDeleteIntegrationTest : AbstractCriteriaDeleteIntegrationTest() {
    override lateinit var entityManager: EntityManager
}

package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.set

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.set.AbstractCriteriaQueryDslUpdateByIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class EclipselinkCriteriaQueryUpdateDslTest : AbstractCriteriaQueryDslUpdateByIntegrationTest() {
    override lateinit var entityManager: EntityManager
}

package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.from

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.from.AbstractCriteriaQueryDslFromIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class EclipselinkCriteriaQueryDslFromIntegrationTest : AbstractCriteriaQueryDslFromIntegrationTest() {
    override lateinit var entityManager: EntityManager
}

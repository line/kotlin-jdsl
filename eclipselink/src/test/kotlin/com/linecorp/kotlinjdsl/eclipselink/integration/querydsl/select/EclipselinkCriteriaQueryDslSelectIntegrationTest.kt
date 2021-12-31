package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.select

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.select.AbstractCriteriaQueryDslSelectIntegrationTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class EclipselinkCriteriaQueryDslSelectIntegrationTest : AbstractCriteriaQueryDslSelectIntegrationTest() {
    override lateinit var entityManager: EntityManager

    @Test
    override fun `listQuery - select single subquery`() {
        // https://wiki.eclipse.org/EclipseLink/UserGuide/JPA/Basic_JPA_Development/Querying/JPQL#Sub-selects_in_FROM_clause
        // eclipselink does not support select subquery and jpa criteria does not support from subquery
        // so kotlin-jdsl's eclipselink does not support select subquery column
    }
}

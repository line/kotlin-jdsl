package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.groupby

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.groupby.AbstractCriteriaQueryDslGroupByIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class EclipselinkCriteriaQueryDslGroupByIntegrationTest : AbstractCriteriaQueryDslGroupByIntegrationTest() {
    override lateinit var entityManager: EntityManager
}

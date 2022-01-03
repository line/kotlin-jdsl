package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.orderby

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.orderby.AbstractCriteriaQueryDslOrderByIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class EclipselinkCriteriaQueryDslOrderByIntegrationTest : AbstractCriteriaQueryDslOrderByIntegrationTest() {
    override lateinit var entityManager: EntityManager
}

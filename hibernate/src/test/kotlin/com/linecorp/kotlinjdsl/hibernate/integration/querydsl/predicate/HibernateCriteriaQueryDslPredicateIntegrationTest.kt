package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.predicate

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.predicate.AbstractCriteriaQueryDslPredicateIntegrationTest
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
internal class HibernateCriteriaQueryDslPredicateIntegrationTest : AbstractCriteriaQueryDslPredicateIntegrationTest() {
    override lateinit var entityManager: EntityManager
}
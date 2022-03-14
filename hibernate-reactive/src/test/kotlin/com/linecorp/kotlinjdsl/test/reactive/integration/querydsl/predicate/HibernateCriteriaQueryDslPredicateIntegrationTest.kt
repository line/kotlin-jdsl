package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.predicate

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.predicate.AbstractCriteriaQueryDslPredicateIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslPredicateIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslPredicateIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

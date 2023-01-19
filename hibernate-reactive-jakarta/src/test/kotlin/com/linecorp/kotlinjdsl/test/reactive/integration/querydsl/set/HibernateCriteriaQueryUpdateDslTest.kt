package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.set

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.set.AbstractCriteriaQueryDslUpdateByIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import jakarta.persistence.EntityManagerFactory

class HibernateCriteriaQueryUpdateDslTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslUpdateByIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

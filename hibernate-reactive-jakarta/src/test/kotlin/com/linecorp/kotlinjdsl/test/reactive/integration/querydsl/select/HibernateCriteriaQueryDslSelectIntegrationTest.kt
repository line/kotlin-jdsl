package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.select

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.select.AbstractCriteriaQueryDslSelectIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import jakarta.persistence.EntityManagerFactory

class HibernateCriteriaQueryDslSelectIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslSelectIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

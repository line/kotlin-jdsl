package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.from

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.from.AbstractCriteriaQueryDslFromIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import jakarta.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslFromIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslFromIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

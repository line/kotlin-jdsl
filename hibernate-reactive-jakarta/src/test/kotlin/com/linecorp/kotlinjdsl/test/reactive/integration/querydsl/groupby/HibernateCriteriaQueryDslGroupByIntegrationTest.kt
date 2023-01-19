package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.groupby

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.groupby.AbstractCriteriaQueryDslGroupByIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import jakarta.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslGroupByIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslGroupByIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

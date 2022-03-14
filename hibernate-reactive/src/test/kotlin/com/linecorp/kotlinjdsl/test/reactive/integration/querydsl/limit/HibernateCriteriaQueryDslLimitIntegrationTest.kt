package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.limit

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.limit.AbstractCriteriaQueryDslLimitIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslLimitIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslLimitIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

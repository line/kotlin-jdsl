package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.having

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.having.AbstractCriteriaQueryDslHavingIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslHavingIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslHavingIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

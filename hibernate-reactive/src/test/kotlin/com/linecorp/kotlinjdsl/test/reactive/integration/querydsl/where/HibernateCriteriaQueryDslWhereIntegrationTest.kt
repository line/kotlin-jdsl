package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.where

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.where.AbstractCriteriaQueryDslWhereIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslWhereIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslWhereIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

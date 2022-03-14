package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.orderby

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.orderby.AbstractCriteriaQueryDslOrderByIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslOrderByIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslOrderByIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

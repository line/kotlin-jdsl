package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.having

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.having.AbstractCriteriaQueryDslHavingIntegrationTest
import org.hibernate.reactive.stage.Stage
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslHavingIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslHavingIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

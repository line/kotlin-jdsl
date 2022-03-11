package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.from

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.from.AbstractCriteriaQueryDslFromIntegrationTest
import org.hibernate.reactive.stage.Stage
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslFromIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslFromIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

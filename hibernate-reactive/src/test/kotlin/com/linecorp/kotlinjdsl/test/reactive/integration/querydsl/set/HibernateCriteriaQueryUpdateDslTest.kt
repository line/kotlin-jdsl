package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.set

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.set.AbstractCriteriaQueryDslUpdateByIntegrationTest
import org.hibernate.reactive.stage.Stage
import javax.persistence.EntityManagerFactory

class HibernateCriteriaQueryUpdateDslTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslUpdateByIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

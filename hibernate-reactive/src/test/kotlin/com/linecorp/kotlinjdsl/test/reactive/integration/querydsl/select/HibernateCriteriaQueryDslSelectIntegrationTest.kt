package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.select

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.select.AbstractCriteriaQueryDslSelectIntegrationTest
import org.hibernate.reactive.stage.Stage

class HibernateCriteriaQueryDslSelectIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslSelectIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
}

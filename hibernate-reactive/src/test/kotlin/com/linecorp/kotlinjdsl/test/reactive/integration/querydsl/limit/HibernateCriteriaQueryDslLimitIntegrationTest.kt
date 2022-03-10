package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.limit

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.limit.AbstractCriteriaQueryDslLimitIntegrationTest
import org.hibernate.reactive.stage.Stage

internal class HibernateCriteriaQueryDslLimitIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslLimitIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
}
package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.where

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.where.AbstractCriteriaQueryDslWhereIntegrationTest
import org.hibernate.reactive.stage.Stage

internal class HibernateCriteriaQueryDslWhereIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslWhereIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
}

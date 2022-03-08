package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.orderby

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.orderby.AbstractCriteriaQueryDslOrderByIntegrationTest
import org.hibernate.reactive.stage.Stage

internal class HibernateCriteriaQueryDslOrderByIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslOrderByIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
}

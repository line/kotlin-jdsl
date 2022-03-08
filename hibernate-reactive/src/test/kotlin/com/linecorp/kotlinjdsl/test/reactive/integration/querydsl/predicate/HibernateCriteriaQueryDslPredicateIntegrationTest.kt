package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.predicate

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.predicate.AbstractCriteriaQueryDslPredicateIntegrationTest
import org.hibernate.reactive.stage.Stage

internal class HibernateCriteriaQueryDslPredicateIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslPredicateIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
}

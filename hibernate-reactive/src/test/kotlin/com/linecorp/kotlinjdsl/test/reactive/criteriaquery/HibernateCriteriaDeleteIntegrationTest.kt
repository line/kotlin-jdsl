package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import org.hibernate.reactive.stage.Stage.SessionFactory

class HibernateCriteriaDeleteIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaDeleteIntegrationTest<SessionFactory>() {
    override lateinit var factory: SessionFactory
}

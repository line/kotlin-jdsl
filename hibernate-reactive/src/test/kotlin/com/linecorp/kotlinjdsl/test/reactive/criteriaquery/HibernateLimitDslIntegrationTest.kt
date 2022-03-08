package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import org.hibernate.reactive.stage.Stage

class HibernateLimitDslIntegrationTest : HibernateCriteriaIntegrationTest, AbstractLimitDslTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
}

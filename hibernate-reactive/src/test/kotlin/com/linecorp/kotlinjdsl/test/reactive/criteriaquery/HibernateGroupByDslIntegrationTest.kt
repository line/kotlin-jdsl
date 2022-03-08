package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import org.hibernate.reactive.stage.Stage

class HibernateGroupByDslIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractGroupByDslTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
}

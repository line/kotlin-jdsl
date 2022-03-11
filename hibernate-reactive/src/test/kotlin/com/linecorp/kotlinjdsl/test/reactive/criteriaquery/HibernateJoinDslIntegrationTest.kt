package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import org.hibernate.reactive.stage.Stage
import javax.persistence.EntityManagerFactory

class HibernateJoinDslIntegrationTest : HibernateCriteriaIntegrationTest, AbstractJoinDslTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

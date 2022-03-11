package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import org.hibernate.reactive.stage.Stage.SessionFactory
import javax.persistence.EntityManagerFactory

class HibernateFetchDslIntegrationTest : HibernateCriteriaIntegrationTest, AbstractFetchDslTest<SessionFactory>() {
    override lateinit var factory: SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

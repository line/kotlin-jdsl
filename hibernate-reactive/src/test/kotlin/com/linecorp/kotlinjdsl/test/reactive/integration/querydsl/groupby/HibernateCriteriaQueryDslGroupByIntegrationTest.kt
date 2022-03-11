package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.groupby

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.groupby.AbstractCriteriaQueryDslGroupByIntegrationTest
import org.hibernate.reactive.stage.Stage
import javax.persistence.EntityManagerFactory

internal class HibernateCriteriaQueryDslGroupByIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslGroupByIntegrationTest<Stage.SessionFactory>() {
    override lateinit var factory: Stage.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

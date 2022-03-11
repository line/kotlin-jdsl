package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.EntityManagerFactory

class HibernateOrderByDslIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractOrderByDslTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory
}

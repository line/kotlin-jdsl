package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.from

import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.querydsl.from.AbstractCriteriaQueryDslFromTreatIntegrationTest
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.Test
import jakarta.persistence.EntityManagerFactory

/**
 * https://discourse.hibernate.org/t/jpa-downcast-criteria-treat-vs-jpql-treat/2231
 *
 * This isn't the case in all cases, and if the root and parent entities are the same when downcasting, there's no problem.
 * However, if the root and parent entities are different and an attempt to downcast them to a child entity is not used,
 * however, an additional join occurs in the query statement.
 * In this case, if you treat distinct as in the test example, there is no problem.
 */
internal class HibernateCriteriaQueryDslFromTreatIntegrationTest : HibernateCriteriaIntegrationTest,
    AbstractCriteriaQueryDslFromTreatIntegrationTest<Mutiny.SessionFactory>() {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory

    /**
     * https://discourse.hibernate.org/t/can-fetch-be-used-as-parameter-of-treat-for-downcasting/3301
     * An UnsupportedOperationException is raised when fetching an entity intended for downcasting.
     * The solution is to use the method in the link, Criteria API for Hibernate,
     * but we use JPA standard Criteria API, so it is difficult to support until Hibernate fixes the bug.
     */
    @Test
    override fun getProjectsWithSupervisorSalaryEqualBySubqueryFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalaryWithFetch() {
        assertThatThrownBy { super.getProjectsWithSupervisorSalaryEqualBySubqueryFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalaryWithFetch() }
            .isInstanceOf(UnsupportedOperationException::class.java)
    }
}

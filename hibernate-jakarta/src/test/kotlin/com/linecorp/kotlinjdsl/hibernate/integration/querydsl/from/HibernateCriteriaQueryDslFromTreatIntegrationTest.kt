package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.from

import com.linecorp.kotlinjdsl.test.integration.querydsl.from.AbstractCriteriaQueryDslFromTreatIntegrationTest
import org.hibernate.sql.ast.SqlTreeCreationException
import org.junit.jupiter.api.Test

/**
 * https://discourse.hibernate.org/t/jpa-downcast-criteria-treat-vs-jpql-treat/2231
 *
 * This isn't the case in all cases, and if the root and parent entities are the same when downcasting, there's no problem.
 * However, if the root and parent entities are different and an attempt to downcast them to a child entity is not used,
 * however, an additional join occurs in the query statement.
 * In this case, if you treat distinct as in the test example, there is no problem.
 */
internal class HibernateCriteriaQueryDslFromTreatIntegrationTest : AbstractCriteriaQueryDslFromTreatIntegrationTest() {

    /**
     * Unlike hibernate 5, subquery in select is not properly supported in 6.
     * As the hibernate version upgrade progresses in the future,
     * we expect this test to break and operate normally.
     */
    override fun getProjectByFullTimeEmployeesSalarySelectFullTimeEmployee() {
        assertThatThrownBy { super.getProjectByFullTimeEmployeesSalarySelectFullTimeEmployee() }
            .hasMessageContaining("Could not locate TableGroup - treat")
            .hasRootCauseExactlyInstanceOf(SqlTreeCreationException::class.java)
    }
}

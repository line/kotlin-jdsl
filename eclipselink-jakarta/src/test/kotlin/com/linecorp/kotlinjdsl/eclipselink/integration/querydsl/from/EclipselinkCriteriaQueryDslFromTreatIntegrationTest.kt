package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.from

import com.linecorp.kotlinjdsl.test.integration.querydsl.from.AbstractCriteriaQueryDslFromTreatIntegrationTest
import org.eclipse.persistence.exceptions.QueryException
import org.junit.jupiter.api.Test

internal class EclipselinkCriteriaQueryDslFromTreatIntegrationTest :
    AbstractCriteriaQueryDslFromTreatIntegrationTest() {
    /**
     * except Hibernate, select downcasting entity is prohibited
     */
    @Test
    override fun getProjectByFullTimeEmployeesSalarySelectFullTimeEmployee() {
        assertThatThrownBy { super.getProjectByFullTimeEmployeesSalarySelectFullTimeEmployee() }
            .hasRootCauseExactlyInstanceOf(QueryException::class.java)
    }
}

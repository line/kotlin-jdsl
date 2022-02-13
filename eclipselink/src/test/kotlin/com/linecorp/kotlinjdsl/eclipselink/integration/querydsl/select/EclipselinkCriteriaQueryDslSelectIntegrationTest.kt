package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.select

import com.linecorp.kotlinjdsl.test.integration.querydsl.select.AbstractCriteriaQueryDslSelectIntegrationTest
import org.eclipse.persistence.internal.jpa.querydef.SelectionImpl
import org.eclipse.persistence.internal.jpa.querydef.SubQueryImpl
import org.junit.jupiter.api.Test

class EclipselinkCriteriaQueryDslSelectIntegrationTest : AbstractCriteriaQueryDslSelectIntegrationTest() {

    /**
     * https://wiki.eclipse.org/EclipseLink/UserGuide/JPA/Basic_JPA_Development/Querying/JPQL#Sub-selects_in_FROM_clause
     * eclipselink does not support subquery in select and jpa criteria does not support from subquery
     * so kotlin-jdsl's eclipselink does not support subquery in select
     */
    @Test
    override fun `listQuery - subquery in select, subquery in from`() {
        // when
        val exception =
            catchThrowable(ClassCastException::class) { super.`listQuery - subquery in select, subquery in from`() }

        // then
        assertThat(exception)
            .isInstanceOf(ClassCastException::class.java)
            .hasMessageContaining(SubQueryImpl::class.qualifiedName)
            .hasMessageContaining("cannot be cast")
            .hasMessageContaining(SelectionImpl::class.qualifiedName)
    }
}

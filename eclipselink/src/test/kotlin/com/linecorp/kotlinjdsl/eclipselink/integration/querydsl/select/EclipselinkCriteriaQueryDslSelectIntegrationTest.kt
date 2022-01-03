package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.select

import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.test.integration.querydsl.select.AbstractCriteriaQueryDslSelectIntegrationTest
import org.eclipse.persistence.internal.jpa.querydef.SelectionImpl
import org.eclipse.persistence.internal.jpa.querydef.SubQueryImpl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class EclipselinkCriteriaQueryDslSelectIntegrationTest : AbstractCriteriaQueryDslSelectIntegrationTest() {
    override lateinit var entityManager: EntityManager

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
            .hasMessageContaining("class ${SubQueryImpl::class.qualifiedName} cannot be cast to class ${SelectionImpl::class.qualifiedName}")
    }
}

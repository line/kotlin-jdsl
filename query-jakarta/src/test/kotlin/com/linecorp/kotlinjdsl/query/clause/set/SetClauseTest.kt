package com.linecorp.kotlinjdsl.query.clause.set

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaUpdate
import jakarta.persistence.criteria.Path

internal class SetClauseTest {
    @Suppress("CAST_NEVER_SUCCEEDS")
    @Test
    fun apply() {
        val froms: Froms = mockk()
        val query: CriteriaUpdate<Any> = mockk()
        val criteriaBuilder: CriteriaBuilder = mockk()

        val column1 = mockk<ColumnSpec<*>>()
        val column2 = mockk<ColumnSpec<*>>()
        val params: Map<ColumnSpec<*>, Any?> = mapOf(column1 to 1234, column2 to null)

        val key1 = mockk<Path<Any>>()
        val key2 = mockk<Path<Any>>()
        every { query.set(key1, 1234) } returns query
        every { query.set(key2, null as? String) } returns query
        every { column1.toCriteriaExpression(froms, query, criteriaBuilder) } returns key1
        every { column2.toCriteriaExpression(froms, query, criteriaBuilder) } returns key2

        SetClause(params).apply(froms, query, criteriaBuilder)

        verify(exactly = 1) {
            query.set(key1, 1234)
            query.set(key2, null as? String)
            column1.toCriteriaExpression(froms, query, criteriaBuilder)
            column2.toCriteriaExpression(froms, query, criteriaBuilder)
        }
    }
}

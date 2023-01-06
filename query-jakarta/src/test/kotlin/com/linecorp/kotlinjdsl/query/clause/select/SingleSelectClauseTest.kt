package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.Subquery

@ExtendWith(MockKExtension::class)
internal class SingleSelectClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var subquery: Subquery<Int>

    @MockK
    private lateinit var criteriaQuery: CriteriaQuery<Int>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun `apply criteria query`() {
        // given
        val expressionSpec: ExpressionSpec<Int> = mockk()
        val expression: Expression<Int> = mockk()

        every { expressionSpec.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder) } returns expression
        every { criteriaQuery.select(expression) } returns criteriaQuery
        every { criteriaQuery.distinct(true) } returns criteriaQuery

        // when
        SingleSelectClause(Int::class.java, distinct = true, expressionSpec)
            .apply(froms, criteriaQuery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)
            criteriaQuery.select(expression)
            criteriaQuery.distinct(true)
        }

        confirmVerified(expressionSpec, froms, criteriaQuery, criteriaBuilder)
    }

    @Test
    fun `apply subquery`() {
        // given
        val expressionSpec: ExpressionSpec<Int> = mockk()
        val expression: Expression<Int> = mockk()

        every { expressionSpec.toCriteriaExpression(froms, subquery, criteriaBuilder) } returns expression
        every { subquery.select(expression) } returns subquery
        every { subquery.distinct(true) } returns subquery

        // when
        SingleSelectClause(Int::class.java, distinct = true, expressionSpec)
            .apply(froms, subquery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, subquery, criteriaBuilder)
            subquery.select(expression)
            subquery.distinct(true)
        }

        confirmVerified(expressionSpec, froms, criteriaQuery, criteriaBuilder)
    }
}

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

@ExtendWith(MockKExtension::class)
internal class CountSingleSelectClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var criteriaQuery: CriteriaQuery<Long>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun `apply - distinct`() {
        // given
        val expressionSpec: ExpressionSpec<*> = mockk()
        val expression: Expression<*> = mockk()

        val countDistinctExpression: Expression<Long> = mockk()

        every { expressionSpec.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder) } returns expression
        every { criteriaBuilder.countDistinct(expression) } returns countDistinctExpression
        every { criteriaQuery.select(countDistinctExpression) } returns criteriaQuery

        // when
        CountSingleSelectClause(distinct = true, expressionSpec).apply(froms, criteriaQuery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)
            criteriaBuilder.countDistinct(expression)
            criteriaQuery.select(countDistinctExpression)
        }

        confirmVerified(expressionSpec, froms, criteriaQuery, criteriaBuilder)
    }

    @Test
    fun `apply - not distinct`() {
        // given
        val expressionSpec: ExpressionSpec<*> = mockk()
        val expression: Expression<*> = mockk()

        val countExpression: Expression<Long> = mockk()

        every { expressionSpec.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder) } returns expression
        every { criteriaBuilder.count(expression) } returns countExpression
        every { criteriaQuery.select(countExpression) } returns criteriaQuery

        // when
        CountSingleSelectClause(distinct = false, expressionSpec).apply(froms, criteriaQuery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)
            criteriaBuilder.count(expression)
            criteriaQuery.select(countExpression)
        }

        confirmVerified(expressionSpec, froms, criteriaQuery, criteriaBuilder)
    }
}

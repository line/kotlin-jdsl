package com.linecorp.kotlinjdsl.query.clause.groupby

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
internal class GroupByClauseTest : WithKotlinJdslAssertions {
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
        val expressionSpec1: ExpressionSpec<*> = mockk()
        val expressionSpec2: ExpressionSpec<*> = mockk()

        val expression1: Expression<*> = mockk()
        val expression2: Expression<*> = mockk()

        every { expressionSpec1.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder) } returns expression1
        every { expressionSpec2.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder) } returns expression2

        every { criteriaQuery.groupBy(listOf(expression1, expression2)) } returns criteriaQuery

        // when
        GroupByClause(listOf(expressionSpec1, expressionSpec2)).apply(froms, criteriaQuery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            expressionSpec1.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)
            expressionSpec2.toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)

            criteriaQuery.groupBy(listOf(expression1, expression2))
        }

        confirmVerified(expressionSpec1, expressionSpec2, froms, subquery, criteriaQuery, criteriaBuilder)
    }

    @Test
    fun `apply criteria query - if expression is empty then do nothing`() {
        // when
        GroupByClause(emptyList()).apply(froms, criteriaQuery, criteriaBuilder)

        // then
        confirmVerified(froms, subquery, criteriaQuery, criteriaBuilder)
    }

    @Test
    fun `apply subquery`() {
        // given
        val expressionSpec1: ExpressionSpec<*> = mockk()
        val expressionSpec2: ExpressionSpec<*> = mockk()

        val expression1: Expression<*> = mockk()
        val expression2: Expression<*> = mockk()

        every { expressionSpec1.toCriteriaExpression(froms, subquery, criteriaBuilder) } returns expression1
        every { expressionSpec2.toCriteriaExpression(froms, subquery, criteriaBuilder) } returns expression2

        every { subquery.groupBy(listOf(expression1, expression2)) } returns subquery

        // when
        GroupByClause(listOf(expressionSpec1, expressionSpec2)).apply(froms, subquery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            expressionSpec1.toCriteriaExpression(froms, subquery, criteriaBuilder)
            expressionSpec2.toCriteriaExpression(froms, subquery, criteriaBuilder)

            subquery.groupBy(listOf(expression1, expression2))
        }

        confirmVerified(expressionSpec1, expressionSpec2, froms, subquery, subquery, criteriaBuilder)
    }

    @Test
    fun `apply subquery - if expression is empty then do nothing`() {
        // when
        GroupByClause(emptyList()).apply(froms, subquery, criteriaBuilder)

        // then
        confirmVerified(froms, subquery, subquery, criteriaBuilder)
    }
}

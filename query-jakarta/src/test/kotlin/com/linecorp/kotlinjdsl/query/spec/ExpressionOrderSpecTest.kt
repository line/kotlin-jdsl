package com.linecorp.kotlinjdsl.query.spec

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
import jakarta.persistence.criteria.Order

@ExtendWith(MockKExtension::class)
internal class ExpressionOrderSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: CriteriaQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun `toCriteriaOrder - asc`() {
        // given
        val expression = mockk<ExpressionSpec<*>>()
        val criteriaExpression = mockk<Expression<*>>()

        val order = mockk<Order>()

        every { expression.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns criteriaExpression
        every { criteriaBuilder.asc(any()) } returns order

        // when
        val spec = ExpressionOrderSpec(expression, ascending = true)

        val actual = spec.toCriteriaOrder(froms, query, criteriaBuilder)

        // then
        assertThat(actual).containsOnly(order)

        verify(exactly = 1) {
            expression.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.asc(criteriaExpression)
        }

        confirmVerified(expression, froms, query, criteriaBuilder)
    }

    @Test
    fun `toCriteriaOrder - desc`() {
        // given
        val expression = mockk<ExpressionSpec<*>>()
        val criteriaExpression = mockk<Expression<*>>()

        val order = mockk<Order>()

        every { expression.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns criteriaExpression
        every { criteriaBuilder.desc(any()) } returns order

        // when
        val spec = ExpressionOrderSpec(expression, ascending = false)

        val actual = spec.toCriteriaOrder(froms, query, criteriaBuilder)

        // then
        assertThat(actual).containsOnly(order)

        verify(exactly = 1) {
            expression.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.desc(criteriaExpression)
        }

        confirmVerified(expression, froms, query, criteriaBuilder)
    }
}

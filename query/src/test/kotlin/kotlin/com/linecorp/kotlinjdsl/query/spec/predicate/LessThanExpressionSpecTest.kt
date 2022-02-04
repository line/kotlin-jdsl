package com.linecorp.kotlinjdsl.query.spec.predicate

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
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Predicate

@ExtendWith(MockKExtension::class)
internal class LessThanExpressionSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun `toCriteriaPredicate - inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val lessThanOrEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression
        every { rightExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns rightExpression

        every {
            criteriaBuilder.lessThanOrEqualTo(any(), any<Expression<Int>>())
        } returns lessThanOrEqualToPredicate

        // when
        val actual = LessThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, true)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(lessThanOrEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.lessThanOrEqualTo(leftExpression, rightExpression)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec,
            froms, query, criteriaBuilder
        )
    }

    @Test
    fun `toCriteriaPredicate - not inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val lessThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression
        every { rightExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns rightExpression

        every { criteriaBuilder.lessThan(any(), any<Expression<Int>>()) } returns lessThanPredicate

        // when
        val actual = LessThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, false)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(lessThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.lessThan(leftExpression, rightExpression)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec,
            froms, query, criteriaBuilder
        )
    }
}
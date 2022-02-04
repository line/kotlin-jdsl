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
internal class BetweenExpressionSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaPredicate() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec1: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec2: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression1: Expression<Int> = mockk()
        val rightExpression2: Expression<Int> = mockk()

        val betweenPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression
        every { rightExpressionSpec1.toCriteriaExpression(any(), any(), any()) } returns rightExpression1
        every { rightExpressionSpec2.toCriteriaExpression(any(), any(), any()) } returns rightExpression2

        every { criteriaBuilder.between(any(), any<Expression<Int>>(), any()) } returns betweenPredicate

        // when
        val actual = BetweenExpressionSpec(
            leftExpressionSpec,
            rightExpressionSpec1,
            rightExpressionSpec2
        ).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(betweenPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec1.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec2.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.between(leftExpression, rightExpression1, rightExpression2)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec1,
            rightExpressionSpec2,
            froms, query, criteriaBuilder
        )
    }
}
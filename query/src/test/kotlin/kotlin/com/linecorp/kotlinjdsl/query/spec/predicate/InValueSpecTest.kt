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
internal class InValueSpecTest : WithKotlinJdslAssertions {
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
        val right1 = 10
        val right2 = 20

        val leftExpression: Expression<Int> = mockk()

        val `in`: CriteriaBuilder.In<Int> = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression

        every { criteriaBuilder.`in`(any<Expression<Int>>()) } returns `in`
        every { `in`.value(right1) } returns `in`
        every { `in`.value(right2) } returns `in`

        // when
        val actual = InValueSpec(leftExpressionSpec, listOf(right1, right2))
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(`in`)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.`in`(leftExpression)
            `in`(right1)
            `in`(right2)
        }

        confirmVerified(leftExpressionSpec, froms, query, criteriaBuilder)
    }

    @Test
    fun `toCriteriaPredicate - empty rights`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()

        val emptyPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns emptyPredicate

        // when
        val actual = InValueSpec(leftExpressionSpec, emptyList())
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(emptyPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(froms, query, criteriaBuilder)
    }
}
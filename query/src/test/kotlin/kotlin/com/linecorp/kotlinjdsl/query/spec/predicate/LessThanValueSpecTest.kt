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
internal class LessThanValueSpecTest : WithKotlinJdslAssertions {
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
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val lessThanEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression

        every { criteriaBuilder.lessThanOrEqualTo(any(), any<Int>()) } returns lessThanEqualToPredicate

        // when
        val actual = LessThanValueSpec(leftExpressionSpec, right, true)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(lessThanEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.lessThanOrEqualTo(leftExpression, right)
        }

        confirmVerified(froms, query, criteriaBuilder)
    }

    @Test
    fun `toCriteriaPredicate - not inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val lessThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression

        every { criteriaBuilder.lessThan(any(), any<Int>()) } returns lessThanPredicate

        // when
        val actual = LessThanValueSpec(leftExpressionSpec, right, false)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(lessThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.lessThan(leftExpression, right)
        }

        confirmVerified(froms, query, criteriaBuilder)
    }
}
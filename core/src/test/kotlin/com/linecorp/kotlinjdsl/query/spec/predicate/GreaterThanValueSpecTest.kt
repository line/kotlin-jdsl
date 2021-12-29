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
internal class GreaterThanValueSpecTest : WithKotlinJdslAssertions {
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

        val greaterThanEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression

        every { criteriaBuilder.greaterThanOrEqualTo(any(), any<Int>()) } returns greaterThanEqualToPredicate

        // when
        val actual = GreaterThanValueSpec(leftExpressionSpec, right, true)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.greaterThanOrEqualTo(leftExpression, right)
        }

        confirmVerified(froms, query, criteriaBuilder)
    }

    @Test
    fun `toCriteriaPredicate - not inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val greaterThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression

        every { criteriaBuilder.greaterThan(any(), any<Int>()) } returns greaterThanPredicate

        // when
        val actual = GreaterThanValueSpec(leftExpressionSpec, right, false)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.greaterThan(leftExpression, right)
        }

        confirmVerified(froms, query, criteriaBuilder)
    }
}
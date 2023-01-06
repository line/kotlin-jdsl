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
import jakarta.persistence.criteria.*

@ExtendWith(MockKExtension::class)
internal class GreaterThanValueSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var updateQuery: CriteriaUpdate<*>

    @MockK
    private lateinit var deleteQuery: CriteriaDelete<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun `toCriteriaPredicate - inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val greaterThanEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns leftExpression

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

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns leftExpression

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

    @Test
    fun `update toCriteriaPredicate - inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val greaterThanEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns leftExpression

        every { criteriaBuilder.greaterThanOrEqualTo(any(), any<Int>()) } returns greaterThanEqualToPredicate

        // when
        val actual = GreaterThanValueSpec(leftExpressionSpec, right, true)
            .toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.greaterThanOrEqualTo(leftExpression, right)
        }

        confirmVerified(froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaPredicate - not inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val greaterThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns leftExpression

        every { criteriaBuilder.greaterThan(any(), any<Int>()) } returns greaterThanPredicate

        // when
        val actual = GreaterThanValueSpec(leftExpressionSpec, right, false)
            .toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.greaterThan(leftExpression, right)
        }

        confirmVerified(froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate - inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val greaterThanEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns leftExpression

        every { criteriaBuilder.greaterThanOrEqualTo(any(), any<Int>()) } returns greaterThanEqualToPredicate

        // when
        val actual = GreaterThanValueSpec(leftExpressionSpec, right, true)
            .toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.greaterThanOrEqualTo(leftExpression, right)
        }

        confirmVerified(froms, deleteQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate - not inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val right = 10

        val leftExpression: Expression<Int> = mockk()

        val greaterThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns leftExpression

        every { criteriaBuilder.greaterThan(any(), any<Int>()) } returns greaterThanPredicate

        // when
        val actual = GreaterThanValueSpec(leftExpressionSpec, right, false)
            .toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.greaterThan(leftExpression, right)
        }

        confirmVerified(froms, deleteQuery, criteriaBuilder)
    }
}

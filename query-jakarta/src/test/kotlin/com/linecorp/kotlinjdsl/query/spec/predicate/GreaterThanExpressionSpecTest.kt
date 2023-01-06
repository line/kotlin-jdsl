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
internal class GreaterThanExpressionSpecTest : WithKotlinJdslAssertions {
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
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val greaterThanOrEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec.toCriteriaExpression(
                any(),
                any<CriteriaQuery<*>>(),
                any()
            )
        } returns rightExpression

        every {
            criteriaBuilder.greaterThanOrEqualTo(any(), any<Expression<Int>>())
        } returns greaterThanOrEqualToPredicate

        // when
        val actual = GreaterThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, true)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanOrEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.greaterThanOrEqualTo(leftExpression, rightExpression)
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

        val greaterThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec.toCriteriaExpression(
                any(),
                any<CriteriaQuery<*>>(),
                any()
            )
        } returns rightExpression

        every { criteriaBuilder.greaterThan(any(), any<Expression<Int>>()) } returns greaterThanPredicate

        // when
        val actual = GreaterThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, false)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.greaterThan(leftExpression, rightExpression)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec,
            froms, query, criteriaBuilder
        )
    }

    @Test
    fun `update toCriteriaPredicate - inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val greaterThanOrEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec.toCriteriaExpression(
                any(),
                any<CriteriaUpdate<*>>(),
                any()
            )
        } returns rightExpression

        every {
            criteriaBuilder.greaterThanOrEqualTo(any(), any<Expression<Int>>())
        } returns greaterThanOrEqualToPredicate

        // when
        val actual = GreaterThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, true)
            .toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanOrEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

            criteriaBuilder.greaterThanOrEqualTo(leftExpression, rightExpression)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec,
            froms, updateQuery, criteriaBuilder
        )
    }

    @Test
    fun `update toCriteriaPredicate - not inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val greaterThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec.toCriteriaExpression(
                any(),
                any<CriteriaUpdate<*>>(),
                any()
            )
        } returns rightExpression

        every { criteriaBuilder.greaterThan(any(), any<Expression<Int>>()) } returns greaterThanPredicate

        // when
        val actual = GreaterThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, false)
            .toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

            criteriaBuilder.greaterThan(leftExpression, rightExpression)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec,
            froms, updateQuery, criteriaBuilder
        )
    }

    @Test
    fun `delete toCriteriaPredicate - inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val greaterThanOrEqualToPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec.toCriteriaExpression(
                any(),
                any<CriteriaDelete<*>>(),
                any()
            )
        } returns rightExpression

        every {
            criteriaBuilder.greaterThanOrEqualTo(any(), any<Expression<Int>>())
        } returns greaterThanOrEqualToPredicate

        // when
        val actual = GreaterThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, true)
            .toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanOrEqualToPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

            criteriaBuilder.greaterThanOrEqualTo(leftExpression, rightExpression)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec,
            froms, deleteQuery, criteriaBuilder
        )
    }

    @Test
    fun `delete toCriteriaPredicate - not inclusive`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val greaterThanPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec.toCriteriaExpression(
                any(),
                any<CriteriaDelete<*>>(),
                any()
            )
        } returns rightExpression

        every { criteriaBuilder.greaterThan(any(), any<Expression<Int>>()) } returns greaterThanPredicate

        // when
        val actual = GreaterThanExpressionSpec(leftExpressionSpec, rightExpressionSpec, false)
            .toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(greaterThanPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

            criteriaBuilder.greaterThan(leftExpression, rightExpression)
        }

        confirmVerified(
            leftExpressionSpec,
            rightExpressionSpec,
            froms, deleteQuery, criteriaBuilder
        )
    }
}

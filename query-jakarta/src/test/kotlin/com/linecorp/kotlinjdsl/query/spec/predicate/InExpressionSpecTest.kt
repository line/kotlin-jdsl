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
internal class InExpressionSpecTest : WithKotlinJdslAssertions {
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
    fun toCriteriaPredicate() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec1: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec2: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression1: Expression<Int> = mockk()
        val rightExpression2: Expression<Int> = mockk()

        val `in`: CriteriaBuilder.In<Int> = mockk()

        every { criteriaBuilder.`in`(any<Expression<Int>>()) } returns `in`
        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec1.toCriteriaExpression(
                any(),
                any<CriteriaQuery<*>>(),
                any()
            )
        } returns rightExpression1
        every {
            rightExpressionSpec2.toCriteriaExpression(
                any(),
                any<CriteriaQuery<*>>(),
                any()
            )
        } returns rightExpression2

        every { `in`.value(rightExpression1) } returns `in`
        every { `in`.value(rightExpression2) } returns `in`

        // when
        val actual = InExpressionSpec(
            leftExpressionSpec,
            listOf(rightExpressionSpec1, rightExpressionSpec2)
        ).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(`in`)

        verify(exactly = 1) {
            criteriaBuilder.`in`(leftExpression)
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec1.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec2.toCriteriaExpression(froms, query, criteriaBuilder)

            `in`.value(rightExpression1)
            `in`.value(rightExpression2)
        }

        confirmVerified(
            leftExpressionSpec, rightExpressionSpec1, rightExpressionSpec2,
            froms, query, criteriaBuilder,
        )
    }

    @Test
    fun `toCriteriaPredicate - empty rights`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()

        val emptyPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns emptyPredicate

        // when
        val actual = InExpressionSpec(
            leftExpressionSpec, emptyList()
        ).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(emptyPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(leftExpressionSpec, froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaPredicate`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec1: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec2: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression1: Expression<Int> = mockk()
        val rightExpression2: Expression<Int> = mockk()

        val `in`: CriteriaBuilder.In<Int> = mockk()

        every { criteriaBuilder.`in`(any<Expression<Int>>()) } returns `in`
        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec1.toCriteriaExpression(
                any(),
                any<CriteriaUpdate<*>>(),
                any()
            )
        } returns rightExpression1
        every {
            rightExpressionSpec2.toCriteriaExpression(
                any(),
                any<CriteriaUpdate<*>>(),
                any()
            )
        } returns rightExpression2

        every { `in`.value(rightExpression1) } returns `in`
        every { `in`.value(rightExpression2) } returns `in`

        // when
        val actual = InExpressionSpec(
            leftExpressionSpec,
            listOf(rightExpressionSpec1, rightExpressionSpec2)
        ).toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(`in`)

        verify(exactly = 1) {
            criteriaBuilder.`in`(leftExpression)
            leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            rightExpressionSpec1.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            rightExpressionSpec2.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

            `in`.value(rightExpression1)
            `in`.value(rightExpression2)
        }

        confirmVerified(
            leftExpressionSpec, rightExpressionSpec1, rightExpressionSpec2,
            froms, updateQuery, criteriaBuilder,
        )
    }

    @Test
    fun `update toCriteriaPredicate - empty rights`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()

        val emptyPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns emptyPredicate

        // when
        val actual = InExpressionSpec(
            leftExpressionSpec, emptyList()
        ).toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(emptyPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(leftExpressionSpec, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec1: ExpressionSpec<Int> = mockk()
        val rightExpressionSpec2: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression1: Expression<Int> = mockk()
        val rightExpression2: Expression<Int> = mockk()

        val `in`: CriteriaBuilder.In<Int> = mockk()

        every { criteriaBuilder.`in`(any<Expression<Int>>()) } returns `in`
        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns leftExpression
        every {
            rightExpressionSpec1.toCriteriaExpression(
                any(),
                any<CriteriaDelete<*>>(),
                any()
            )
        } returns rightExpression1
        every {
            rightExpressionSpec2.toCriteriaExpression(
                any(),
                any<CriteriaDelete<*>>(),
                any()
            )
        } returns rightExpression2

        every { `in`.value(rightExpression1) } returns `in`
        every { `in`.value(rightExpression2) } returns `in`

        // when
        val actual = InExpressionSpec(
            leftExpressionSpec,
            listOf(rightExpressionSpec1, rightExpressionSpec2)
        ).toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(`in`)

        verify(exactly = 1) {
            criteriaBuilder.`in`(leftExpression)
            leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            rightExpressionSpec1.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            rightExpressionSpec2.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

            `in`.value(rightExpression1)
            `in`.value(rightExpression2)
        }

        confirmVerified(
            leftExpressionSpec, rightExpressionSpec1, rightExpressionSpec2,
            froms, deleteQuery, criteriaBuilder,
        )
    }

    @Test
    fun `delete toCriteriaPredicate - empty rights`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int> = mockk()

        val emptyPredicate: Predicate = mockk()

        every { criteriaBuilder.conjunction() } returns emptyPredicate

        // when
        val actual = InExpressionSpec(
            leftExpressionSpec, emptyList()
        ).toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(emptyPredicate)

        verify(exactly = 1) {
            criteriaBuilder.conjunction()
        }

        confirmVerified(leftExpressionSpec, froms, deleteQuery, criteriaBuilder)
    }
}

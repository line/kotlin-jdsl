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
internal class BetweenValueSpecTest : WithKotlinJdslAssertions {
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
        val leftExpressionSpec: ExpressionSpec<Int?> = mockk()
        val right1 = 10
        val right2 = 20

        val leftExpression: Expression<out Int?> = mockk()

        val betweenPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns leftExpression

        every { criteriaBuilder.between(any(), any<Int>(), any()) } returns betweenPredicate

        // when
        val actual = BetweenValueSpec(leftExpressionSpec, right1, right2)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(betweenPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.between(leftExpression, right1, right2)
        }

        confirmVerified(leftExpressionSpec, froms, query, criteriaBuilder)
    }

    @Test
    fun `updateQuery toCriteriaPredicate`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int?> = mockk()
        val right1 = 10
        val right2 = 20

        val leftExpression: Expression<out Int?> = mockk()

        val betweenPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns leftExpression

        every { criteriaBuilder.between(any(), any<Int>(), any()) } returns betweenPredicate

        // when
        val actual = BetweenValueSpec(leftExpressionSpec, right1, right2)
            .toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(betweenPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

            criteriaBuilder.between(leftExpression, right1, right2)
        }

        confirmVerified(leftExpressionSpec, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate`() {
        // given
        val leftExpressionSpec: ExpressionSpec<Int?> = mockk()
        val right1 = 10
        val right2 = 20

        val leftExpression: Expression<out Int?> = mockk()

        val betweenPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns leftExpression

        every { criteriaBuilder.between(any(), any<Int>(), any()) } returns betweenPredicate

        // when
        val actual = BetweenValueSpec(leftExpressionSpec, right1, right2)
            .toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(betweenPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

            criteriaBuilder.between(leftExpression, right1, right2)
        }

        confirmVerified(leftExpressionSpec, froms, deleteQuery, criteriaBuilder)
    }
}

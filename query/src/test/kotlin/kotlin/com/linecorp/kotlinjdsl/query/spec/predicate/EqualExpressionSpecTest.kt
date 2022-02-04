package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
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
internal class EqualExpressionSpecTest : WithKotlinJdslAssertions {
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
        val rightExpressionSpec: ExpressionSpec<Int> = mockk()

        val leftExpression: Expression<Int> = mockk()
        val rightExpression: Expression<Int> = mockk()

        val equalPredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns leftExpression
        every { rightExpressionSpec.toCriteriaExpression(any(), any(), any()) } returns rightExpression

        every { criteriaBuilder.equal(leftExpression, rightExpression) } returns equalPredicate

        // when
        val actual = EqualExpressionSpec(leftExpressionSpec, rightExpressionSpec)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(equalPredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            rightExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)

            criteriaBuilder.equal(leftExpression, rightExpression)
        }
    }
}
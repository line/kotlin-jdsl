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
internal class IsTrueSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaPredicate() {
        // given
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val expression: Expression<Boolean?> = mockk()

        val isTruePredicate: Predicate = mockk()

        every { expressionSpec.toCriteriaExpression(froms, query, criteriaBuilder) } returns expression
        every { criteriaBuilder.isTrue(expression) } returns isTruePredicate

        // when
        val actual = IsTrueSpec(expressionSpec).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(isTruePredicate)

        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.isTrue(expression)
        }

        confirmVerified(expressionSpec, froms, query, criteriaBuilder)
    }
}
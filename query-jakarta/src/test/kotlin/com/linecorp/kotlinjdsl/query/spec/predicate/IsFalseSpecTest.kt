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
internal class IsFalseSpecTest : WithKotlinJdslAssertions {
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
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val expression: Expression<Boolean?> = mockk()

        val isFalsePredicate: Predicate = mockk()

        every { expressionSpec.toCriteriaExpression(froms, query, criteriaBuilder) } returns expression
        every { criteriaBuilder.isFalse(expression) } returns isFalsePredicate

        // when
        val actual = IsFalseSpec(expressionSpec).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(isFalsePredicate)

        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.isFalse(expression)
        }

        confirmVerified(expressionSpec, froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaPredicate`() {
        // given
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val expression: Expression<Boolean?> = mockk()

        val isFalsePredicate: Predicate = mockk()

        every { expressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder) } returns expression
        every { criteriaBuilder.isFalse(expression) } returns isFalsePredicate

        // when
        val actual = IsFalseSpec(expressionSpec).toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(isFalsePredicate)

        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.isFalse(expression)
        }

        confirmVerified(expressionSpec, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate`() {
        // given
        val expressionSpec: ExpressionSpec<Boolean?> = mockk()

        val expression: Expression<Boolean?> = mockk()

        val isFalsePredicate: Predicate = mockk()

        every { expressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder) } returns expression
        every { criteriaBuilder.isFalse(expression) } returns isFalsePredicate

        // when
        val actual = IsFalseSpec(expressionSpec).toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(isFalsePredicate)

        verify(exactly = 1) {
            expressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.isFalse(expression)
        }

        confirmVerified(expressionSpec, froms, deleteQuery, criteriaBuilder)
    }
}

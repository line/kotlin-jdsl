package com.linecorp.kotlinjdsl.query.clause.select

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
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Expression

@ExtendWith(MockKExtension::class)
internal class CountMultiSelectClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var criteriaQuery: CriteriaQuery<Long>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun apply() {
        // given
        val expressionSpec1: ExpressionSpec<*> = mockk()
        val expressionSpec2: ExpressionSpec<*> = mockk()

        val literalExpression: Expression<Int> = mockk()
        val countExpression: Expression<Long> = mockk()

        every { criteriaBuilder.literal(1) } returns literalExpression
        every { criteriaBuilder.count(literalExpression) } returns countExpression
        every { criteriaQuery.select(countExpression) } returns criteriaQuery

        // when
        CountMultiSelectClause(distinct = false, listOf(expressionSpec1, expressionSpec2))
            .apply(froms, criteriaQuery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            criteriaBuilder.literal(1)
            criteriaBuilder.count(literalExpression)

            criteriaQuery.select(countExpression)
        }

        confirmVerified(froms, criteriaQuery, criteriaBuilder)
    }

    @Test
    fun `apply - if it is distinct then throw exception`() {
        // given
        val expressionSpec1: ExpressionSpec<*> = mockk()
        val expressionSpec2: ExpressionSpec<*> = mockk()

        // when
        val exception = catchThrowable(IllegalStateException::class) {
            CountMultiSelectClause(distinct = true, listOf(expressionSpec1, expressionSpec2))
                .apply(froms, criteriaQuery, criteriaBuilder)
        }

        // then
        assertThat(exception).hasMessageContaining(
            "Count distinct does not support multiple columns. Please remove distinct in selection"
        )

        confirmVerified(froms, criteriaQuery, criteriaBuilder)
    }
}

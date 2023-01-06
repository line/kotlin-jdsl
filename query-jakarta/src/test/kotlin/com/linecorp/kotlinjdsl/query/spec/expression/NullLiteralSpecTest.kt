package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
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
internal class NullLiteralSpecTest : WithKotlinJdslAssertions {
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
    fun toCriteriaExpression() {
        toCriteriaExpression { it.toCriteriaExpression(froms, query, criteriaBuilder) }
    }

    @Test
    fun `update toCriteriaExpression`() {
        toCriteriaExpression { it.toCriteriaExpression(froms, updateQuery, criteriaBuilder) }
    }

    @Test
    fun `delete toCriteriaExpression`() {
        toCriteriaExpression { it.toCriteriaExpression(froms, deleteQuery, criteriaBuilder) }
    }

    private fun toCriteriaExpression(predicate: (NullLiteralSpec<String>) -> Expression<String?>) {
        // given
        val expression = mockk<Expression<String?>>()

        every { criteriaBuilder.nullLiteral<String>(any()) } returns expression

        // when
        val spec = NullLiteralSpec(String::class.java)

        val actual = predicate(spec)

        // then
        assertThat(actual).isEqualTo(expression)

        verify(exactly = 1) {
            criteriaBuilder.nullLiteral(String::class.java)
        }

        confirmVerified(froms, query, criteriaBuilder)
    }
}

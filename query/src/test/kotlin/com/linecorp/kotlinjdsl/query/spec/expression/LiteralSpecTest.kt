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
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression

@ExtendWith(MockKExtension::class)
internal class LiteralSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaExpression() {
        // given
        val expression = mockk<Expression<String>>()

        every { criteriaBuilder.literal(any<String>()) } returns expression

        // when
        val spec = LiteralSpec("TEST")

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(expression)

        verify(exactly = 1) {
            criteriaBuilder.literal("TEST")
        }

        confirmVerified(froms, query, criteriaBuilder)
    }
}
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
internal class LikeSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaPredicate() {
        // given
        val leftExpressionSpec: ExpressionSpec<String?> = mockk()
        val right = "%test%"

        val likeExpression: Expression<String?> = mockk()

        val likePredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder) } returns likeExpression
        every { criteriaBuilder.like(likeExpression, right) } returns likePredicate

        // when
        val actual = LikeSpec(leftExpressionSpec, right).toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(likePredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.like(likeExpression, right)
        }

        confirmVerified(leftExpressionSpec, froms, query, criteriaBuilder)
    }
}
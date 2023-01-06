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
internal class LikeSpecTest : WithKotlinJdslAssertions {
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

    @Test
    fun `update toCriteriaPredicate`() {
        // given
        val leftExpressionSpec: ExpressionSpec<String?> = mockk()
        val right = "%test%"

        val likeExpression: Expression<String?> = mockk()

        val likePredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder) } returns likeExpression
        every { criteriaBuilder.like(likeExpression, right) } returns likePredicate

        // when
        val actual = LikeSpec(leftExpressionSpec, right).toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(likePredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.like(likeExpression, right)
        }

        confirmVerified(leftExpressionSpec, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate`() {
        // given
        val leftExpressionSpec: ExpressionSpec<String?> = mockk()
        val right = "%test%"

        val likeExpression: Expression<String?> = mockk()

        val likePredicate: Predicate = mockk()

        every { leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder) } returns likeExpression
        every { criteriaBuilder.like(likeExpression, right) } returns likePredicate

        // when
        val actual = LikeSpec(leftExpressionSpec, right).toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(likePredicate)

        verify(exactly = 1) {
            leftExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.like(likeExpression, right)
        }

        confirmVerified(leftExpressionSpec, froms, deleteQuery, criteriaBuilder)
    }
}

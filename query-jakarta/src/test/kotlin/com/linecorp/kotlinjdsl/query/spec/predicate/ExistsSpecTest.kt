package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
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
internal class ExistsSpecTest : WithKotlinJdslAssertions {
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
        val subqueryExpressionSpec: SubqueryExpressionSpec<Long> = mockk()

        val subquery: Subquery<Long> = mockk()
        val existsPredicate: Predicate = mockk()

        every { subqueryExpressionSpec.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns subquery
        every { criteriaBuilder.exists(any<Subquery<Long>>()) } returns existsPredicate

        // when
        val actual = ExistsSpec(subqueryExpressionSpec)
            .toCriteriaPredicate(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(existsPredicate)

        verify(exactly = 1) {
            subqueryExpressionSpec.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.exists(subquery)
        }

        confirmVerified(subqueryExpressionSpec, froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaPredicate`() {
        // given
        val subqueryExpressionSpec: SubqueryExpressionSpec<Long> = mockk()

        val subquery: Subquery<Long> = mockk()
        val existsPredicate: Predicate = mockk()

        every { subqueryExpressionSpec.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns subquery
        every { criteriaBuilder.exists(any<Subquery<Long>>()) } returns existsPredicate

        // when
        val actual = ExistsSpec(subqueryExpressionSpec)
            .toCriteriaPredicate(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(existsPredicate)

        verify(exactly = 1) {
            subqueryExpressionSpec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.exists(subquery)
        }

        confirmVerified(subqueryExpressionSpec, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaPredicate`() {
        // given
        val subqueryExpressionSpec: SubqueryExpressionSpec<Long> = mockk()

        val subquery: Subquery<Long> = mockk()
        val existsPredicate: Predicate = mockk()

        every { subqueryExpressionSpec.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns subquery
        every { criteriaBuilder.exists(any<Subquery<Long>>()) } returns existsPredicate

        // when
        val actual = ExistsSpec(subqueryExpressionSpec)
            .toCriteriaPredicate(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(existsPredicate)

        verify(exactly = 1) {
            subqueryExpressionSpec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.exists(subquery)
        }

        confirmVerified(subqueryExpressionSpec, froms, deleteQuery, criteriaBuilder)
    }
}

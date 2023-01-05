package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
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
internal class SubqueryExpressionSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var abstractQuery: AbstractQuery<*>

    @MockK
    private lateinit var criteriaQuery: CriteriaQuery<*>

    @MockK
    private lateinit var criteriaDelete: CriteriaDelete<*>

    @MockK
    private lateinit var criteriaUpdate: CriteriaUpdate<*>

    @MockK
    private lateinit var subquery: Subquery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @MockK
    private lateinit var subqueryCreator: SubqueryCreator

    @Test
    fun toCriteriaExpression() {
        // given
        val spec: SubquerySpec<Int> = mockk()
        val createdSubquery: Subquery<Int> = mockk()

        every { subqueryCreator.createQuery(spec, froms, criteriaQuery, criteriaBuilder) } returns createdSubquery

        // when
        val actual = SubqueryExpressionSpec(spec, subqueryCreator)
            .toCriteriaExpression(froms, criteriaQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(createdSubquery)

        verify(exactly = 1) {
            subqueryCreator.createQuery(spec, froms, criteriaQuery, criteriaBuilder)
        }

        confirmVerified(froms, abstractQuery, criteriaQuery, subquery, criteriaBuilder, subqueryCreator)
    }

    @Test
    fun `toCriteriaExpression - by criteria update query`() {
        // given
        val spec: SubquerySpec<Int> = mockk()
        val createdSubquery: Subquery<Int> = mockk()

        every { subqueryCreator.createQuery(spec, froms, criteriaUpdate, criteriaBuilder) } returns createdSubquery

        // when
        val actual = SubqueryExpressionSpec(spec, subqueryCreator)
            .toCriteriaExpression(froms, criteriaUpdate, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(createdSubquery)

        verify(exactly = 1) {
            subqueryCreator.createQuery(spec, froms, criteriaUpdate, criteriaBuilder)
        }

        confirmVerified(froms, abstractQuery, criteriaUpdate, subquery, criteriaBuilder, subqueryCreator)
    }

    @Test
    fun `toCriteriaExpression - by criteria delete query`() {
        // given
        val spec: SubquerySpec<Int> = mockk()
        val createdSubquery: Subquery<Int> = mockk()

        every { subqueryCreator.createQuery(spec, froms, criteriaDelete, criteriaBuilder) } returns createdSubquery

        // when
        val actual = SubqueryExpressionSpec(spec, subqueryCreator)
            .toCriteriaExpression(froms, criteriaDelete, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(createdSubquery)

        verify(exactly = 1) {
            subqueryCreator.createQuery(spec, froms, criteriaDelete, criteriaBuilder)
        }

        confirmVerified(froms, abstractQuery, criteriaDelete, subquery, criteriaBuilder, subqueryCreator)
    }

    @Test
    fun `toCriteriaExpression - by subquery`() {
        // given
        val spec: SubquerySpec<Int> = mockk()
        val createdSubquery: Subquery<Int> = mockk()

        every { subqueryCreator.createQuery(spec, froms, subquery, criteriaBuilder) } returns createdSubquery

        // when
        val actual = SubqueryExpressionSpec(spec, subqueryCreator)
            .toCriteriaExpression(froms, subquery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(createdSubquery)

        verify(exactly = 1) {
            subqueryCreator.createQuery(spec, froms, subquery, criteriaBuilder)
        }

        confirmVerified(froms, abstractQuery, criteriaQuery, subquery, criteriaBuilder, subqueryCreator)
    }
}

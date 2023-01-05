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
internal class CountSpecTest : WithKotlinJdslAssertions {
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
    fun `toCriteriaExpression - distinct`() {
        // given
        val expression = mockk<ExpressionSpec<Int>>()
        val columnExpression = mockk<Expression<Int>>()

        val countExpression = mockk<Expression<Long>>()

        every { expression.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns columnExpression
        every { criteriaBuilder.countDistinct(any<Expression<Int>>()) } returns countExpression

        // when
        val spec = CountSpec(distinct = true, expression)

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(countExpression)

        verify(exactly = 1) {
            expression.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.countDistinct(columnExpression)
        }

        confirmVerified(expression, froms, query, criteriaBuilder)
    }

    @Test
    fun `toCriteriaExpression - non distinct`() {
        // given
        val column = mockk<ColumnSpec<Int>>()
        val columnExpression = mockk<Expression<Int>>()

        val countExpression = mockk<Expression<Long>>()

        every { column.toCriteriaExpression(any(), any<CriteriaQuery<*>>(), any()) } returns columnExpression
        every { criteriaBuilder.count(any<Expression<Int>>()) } returns countExpression

        // when
        val spec = CountSpec(distinct = false, column)

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(countExpression)

        verify(exactly = 1) {
            column.toCriteriaExpression(froms, query, criteriaBuilder)
            criteriaBuilder.count(columnExpression)
        }

        confirmVerified(column, froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaExpression - distinct`() {
        // given
        val column = mockk<ColumnSpec<Int>>()
        val columnExpression = mockk<Expression<Int>>()

        val countExpression = mockk<Expression<Long>>()

        every { column.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns columnExpression
        every { criteriaBuilder.countDistinct(any<Expression<Int>>()) } returns countExpression

        // when
        val spec = CountSpec(distinct = true, column)

        val actual = spec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(countExpression)

        verify(exactly = 1) {
            column.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.countDistinct(columnExpression)
        }

        confirmVerified(column, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaExpression - non distinct`() {
        // given
        val column = mockk<ColumnSpec<Int>>()
        val columnExpression = mockk<Expression<Int>>()

        val countExpression = mockk<Expression<Long>>()

        every { column.toCriteriaExpression(any(), any<CriteriaUpdate<*>>(), any()) } returns columnExpression
        every { criteriaBuilder.count(any<Expression<Int>>()) } returns countExpression

        // when
        val spec = CountSpec(distinct = false, column)

        val actual = spec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(countExpression)

        verify(exactly = 1) {
            column.toCriteriaExpression(froms, updateQuery, criteriaBuilder)
            criteriaBuilder.count(columnExpression)
        }

        confirmVerified(column, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaExpression - distinct`() {
        // given
        val column = mockk<ColumnSpec<Int>>()
        val columnExpression = mockk<Expression<Int>>()

        val countExpression = mockk<Expression<Long>>()

        every { column.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns columnExpression
        every { criteriaBuilder.countDistinct(any<Expression<Int>>()) } returns countExpression

        // when
        val spec = CountSpec(distinct = true, column)

        val actual = spec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(countExpression)

        verify(exactly = 1) {
            column.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.countDistinct(columnExpression)
        }

        confirmVerified(column, froms, deleteQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaExpression - non distinct`() {
        // given
        val column = mockk<ColumnSpec<Int>>()
        val columnExpression = mockk<Expression<Int>>()

        val countExpression = mockk<Expression<Long>>()

        every { column.toCriteriaExpression(any(), any<CriteriaDelete<*>>(), any()) } returns columnExpression
        every { criteriaBuilder.count(any<Expression<Int>>()) } returns countExpression

        // when
        val spec = CountSpec(distinct = false, column)

        val actual = spec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(countExpression)

        verify(exactly = 1) {
            column.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)
            criteriaBuilder.count(columnExpression)
        }

        confirmVerified(column, froms, deleteQuery, criteriaBuilder)
    }
}

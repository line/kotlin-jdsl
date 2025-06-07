package com.linecorp.kotlinjdsl.querymodel.jpql.set.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.set.SetOperationType
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class JpqlSetOperationQueryTest : WithAssertions {
    private val stringKClass: KClass<String> = String::class

    @Test
    fun `constructor with default values`() {
        // Given
        val leftQuery = mockk<SelectQuery<String>>()
        val rightQuery = mockk<SelectQuery<String>>()

        every { leftQuery.returnType } returns stringKClass
        every { rightQuery.returnType } returns stringKClass

        // When
        val query = JpqlSetOperationQuery(
            leftQuery = leftQuery,
            operationType = SetOperationType.UNION,
            rightQuery = rightQuery,
        )

        // Then
        assertThat(query.returnType).isEqualTo(stringKClass)
        assertThat(query.leftQuery).isEqualTo(leftQuery)
        assertThat(query.operationType).isEqualTo(SetOperationType.UNION)
        assertThat(query.rightQuery).isEqualTo(rightQuery)
        assertThat(query.orderBy).isNull()
    }

    @Test
    fun `constructor with all values set`() {
        // Given
        val leftQuery = mockk<SelectQuery<String>>()
        val rightQuery = mockk<SelectQuery<String>>()
        val sort1 = mockk<Sort>()
        val sort2 = mockk<Sort>()
        val orderByClause = listOf(sort1, sort2)

        every { leftQuery.returnType } returns stringKClass
        every { rightQuery.returnType } returns stringKClass

        // When
        val query = JpqlSetOperationQuery(
            leftQuery = leftQuery,
            operationType = SetOperationType.UNION_ALL,
            rightQuery = rightQuery,
            orderBy = orderByClause,
        )

        // Then
        assertThat(query.returnType).isEqualTo(stringKClass)
        assertThat(query.leftQuery).isEqualTo(leftQuery)
        assertThat(query.operationType).isEqualTo(SetOperationType.UNION_ALL)
        assertThat(query.rightQuery).isEqualTo(rightQuery)
        assertThat(query.orderBy).isEqualTo(orderByClause)
    }
}

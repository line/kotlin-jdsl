package com.linecorp.kotlinjdsl.query.clause.orderby

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.OrderSpec
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
import jakarta.persistence.criteria.Order

@ExtendWith(MockKExtension::class)
internal class OrderByClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var criteriaQuery: CriteriaQuery<Int>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun apply() {
        // given
        val orderSpec1: OrderSpec = mockk()
        val orderSpec2: OrderSpec = mockk()

        val order1: Order = mockk()
        val order2: Order = mockk()
        val order3: Order = mockk()

        every { orderSpec1.toCriteriaOrder(froms, criteriaQuery, criteriaBuilder) } returns listOf(order1)
        every { orderSpec2.toCriteriaOrder(froms, criteriaQuery, criteriaBuilder) } returns listOf(order2, order3)

        every { criteriaQuery.orderBy(listOf(order1, order2, order3)) } returns criteriaQuery

        // when
        OrderByClause(listOf(orderSpec1, orderSpec2)).apply(froms, criteriaQuery, criteriaBuilder)

        // then
        verify(exactly = 1) {
            orderSpec1.toCriteriaOrder(froms, criteriaQuery, criteriaBuilder)
            orderSpec2.toCriteriaOrder(froms, criteriaQuery, criteriaBuilder)
            criteriaQuery.orderBy(listOf(order1, order2, order3))
        }

        confirmVerified(orderSpec1, orderSpec2, froms, criteriaQuery, criteriaBuilder)
    }

    @Test
    fun `apply - empty`() {
        // when
        OrderByClause(emptyList()).apply(froms, criteriaQuery, criteriaBuilder)

        // then
        confirmVerified(froms, criteriaQuery, criteriaBuilder)
    }
}

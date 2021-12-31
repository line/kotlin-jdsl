package com.linecorp.kotlinjdsl.test.integration.querydsl.limit

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslLimitIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val order1 = order { }
    private val order2 = order { }
    private val order3 = order { }

    @BeforeEach
    fun setUp() {
        entityManager.persistAllFlushAndClearEach(order1, order2, order3)
    }

    @Test
    fun offset() {
        // when
        val orderIds = queryFactory.listQuery<Long> {
            select(col(Order::id))
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
            offset(1)
        }

        // then
        assertThat(orderIds).containsOnly(order2.id, order3.id)
    }

    @Test
    fun maxResults() {
        // when
        val orderIds = queryFactory.listQuery<Long> {
            select(col(Order::id))
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
            maxResults(1)
        }

        // then
        assertThat(orderIds).containsOnly(order1.id)
    }

    @Test
    fun limit() {
        // when
        val orderIds = queryFactory.listQuery<Long> {
            select(col(Order::id))
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
            limit(1, 1)
        }

        // then
        assertThat(orderIds).containsOnly(order2.id)
    }
}

package com.linecorp.kotlinjdsl.test.integration.querydsl.orderby

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslOrderByIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val order1 = order { }
    private val order2 = order { }

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(order1, order2)
        entityManager.flushAndClear()
    }

    @Test
    fun asc() {
        // when
        val orderIds = queryFactory.listQuery {
            select(col(Order::id))
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order1.id, order2.id).sorted())
    }

    @Test
    fun desc() {
        // when
        val orderIds = queryFactory.listQuery {
            select(col(Order::id))
            from(entity(Order::class))
            orderBy(col(Order::id).desc())
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order2.id, order1.id).sortedDescending())
    }
}

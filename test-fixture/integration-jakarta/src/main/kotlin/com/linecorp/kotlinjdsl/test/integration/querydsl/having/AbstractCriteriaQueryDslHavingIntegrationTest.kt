package com.linecorp.kotlinjdsl.test.integration.querydsl.having

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslHavingIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(order1, order2, order3)
        entityManager.flushAndClear()
    }

    @Test
    fun having() {
        // when
        val purchaserIds = queryFactory.listQuery {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            groupBy(col(Order::purchaserId))
            having(count(Order::purchaserId).greaterThan(1))
        }

        // then
        assertThat(purchaserIds).containsOnly(1000)
    }
}

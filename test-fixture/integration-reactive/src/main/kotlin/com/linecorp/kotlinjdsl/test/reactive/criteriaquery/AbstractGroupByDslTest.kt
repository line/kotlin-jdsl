package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import kotlinx.coroutines.future.await
import org.junit.jupiter.api.Test

abstract class AbstractGroupByDslTest<S> : CriteriaQueryDslIntegrationTest<S>, WithKotlinJdslAssertions {
    @Test
    fun groupBy() = runBlocking {
        // given
        data class Row(
            val purchaserId: Long,
            val quantity: Long,
        )

        val orderItem1 = orderItem { productId = 100; quantity = 5 }
        val orderItem2 = orderItem { productId = 200; quantity = 2 }
        val orderItem3 = orderItem { productId = 200; quantity = 1 }

        val order1 = order {
            purchaserId = 1000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1) })
        }

        val order2 = order {
            purchaserId = 2000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem2, orderItem3) })
        }

        persistAll(order1, order2)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Row> {
                selectMulti(col(Order::purchaserId), sum(col(OrderItem::quantity)))
                from(entity(Order::class))
                join(Order::groups)
                join(OrderGroup::items)
                groupBy(col(Order::purchaserId))
            }.await()
        }

        // then
        assertThat(query)
            .hasSize(2)
            .containsOnly(
                Row(purchaserId = 1000, quantity = 5),
                Row(purchaserId = 2000, quantity = 3),
            )
    }

    @Test
    fun having() = runBlocking {
        // given
        val orderItem1 = orderItem { productId = 100; quantity = 5 }
        val orderItem2 = orderItem { productId = 200; quantity = 2 }
        val orderItem3 = orderItem { productId = 200; quantity = 1 }

        val order1 = order {
            purchaserId = 1000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1) })
        }

        val order2 = order {
            purchaserId = 2000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem2, orderItem3) })
        }

        persistAll(order1, order2)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                join(Order::groups)
                join(OrderGroup::items)
                groupBy(col(Order::purchaserId))
                having(sum(col(OrderItem::quantity)).greaterThan(4))
            }.await()
        }

        // then
        assertThat(query)
            .hasSize(1)
            .containsOnly(1000L)
    }
}

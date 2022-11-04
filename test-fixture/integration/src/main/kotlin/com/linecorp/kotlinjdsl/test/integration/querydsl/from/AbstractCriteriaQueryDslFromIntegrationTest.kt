package com.linecorp.kotlinjdsl.test.integration.querydsl.from

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.test.entity.Address
import com.linecorp.kotlinjdsl.test.entity.delivery.Delivery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslFromIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val orderItem1 = orderItem { price = 10 }
    private val orderItem2 = orderItem { price = 20 }
    private val orderItem3 = orderItem { price = 30 }
    private val orderItem4 = orderItem { price = 0 }

    private val group1 = orderGroup { items = hashSetOf(orderItem1) }
    private val group2 = orderGroup { items = hashSetOf(orderItem2) }
    private val group3 = orderGroup { items = hashSetOf(orderItem3, orderItem4) }

    private val order1 = order { purchaserId = 1000; groups = hashSetOf(group1) }
    private val order2 = order { purchaserId = 1000; groups = hashSetOf(group2) }
    private val order3 = order { purchaserId = 2000; groups = hashSetOf(group3) }

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(order1, order2, order3)
        entityManager.flushAndClear()
    }

    @Test
    fun join() {
        // when
        val purchaserIds = queryFactory.listQuery {
            selectDistinct(col(Order::id))
            from(entity(Order::class))
            join(Order::groups)
            join(OrderGroup::items)
            where(col(OrderItem::price).greaterThanOrEqualTo(20.toBigDecimal()))
        }

        // then
        assertThat(purchaserIds).containsOnly(order2.id, order3.id)
    }

    @Test
    fun crossJoin() {
        // given
        val delivery1 = delivery { orderId = order1.id }
        val delivery2 = delivery { orderId = order2.id }
        val delivery3 = delivery { orderId = order3.id }

        entityManager.persistAll(delivery1, delivery2, delivery3)
        entityManager.flushAndClear()

        // when
        val purchaserIds = queryFactory.listQuery<Long> {
            selectDistinct(col(Delivery::id))
            from(entity(Delivery::class))
            join(Order::class, on { col(Delivery::orderId).equal(col(Order::id)) })
            where(col(Order::purchaserId).equal(1000))
        }

        // then
        assertThat(purchaserIds).containsOnly(delivery1.id, delivery2.id)
    }

    @Test
    fun associate() {
        // given
        val delivery1 = delivery { orderId = order1.id }
        val delivery2 = delivery { orderId = order2.id }
        val delivery3 = delivery { orderId = order3.id }

        entityManager.persistAll(delivery1, delivery2, delivery3)
        entityManager.flushAndClear()

        // when
        val zipCodes = queryFactory.listQuery {
            selectDistinct(col(Address::zipCode))
            from(entity(Delivery::class))
            join(Order::class, on { col(Delivery::orderId).equal(col(Order::id)) })
            join(Order::groups)
            join(OrderGroup::address)
            associate(OrderAddress::class, Address::class, on(OrderAddress::address))
            where(col(Order::purchaserId).equal(1000))
        }

        // then
        assertThat(zipCodes)
            .hasSize(1)
            .containsOnly(order1.groups.first().address.address.zipCode)
    }


    @Test
    fun fetch() {
        // when
        val order = queryFactory.singleQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            fetch(Order::groups)
            where(col(Order::id).equal(order1.id))
        }

        entityManager.detach(order)

        // then
        assertThat(order.groups.map { it.id }).containsOnly(group1.id)
    }
}

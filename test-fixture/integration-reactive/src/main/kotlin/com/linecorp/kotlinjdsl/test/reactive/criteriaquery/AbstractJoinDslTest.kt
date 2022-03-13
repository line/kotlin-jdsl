package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.Address
import com.linecorp.kotlinjdsl.test.entity.delivery.Delivery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.junit.jupiter.api.Test

abstract class AbstractJoinDslTest<S> : CriteriaQueryDslIntegrationTest<S>, WithKotlinJdslAssertions {
    @Test
    fun joinOneToOne() = runBlocking {
        // given
        val address1 = orderAddress { }
        val group1 = orderGroup { address = address1 }
        val order1 = order { groups = hashSetOf(group1) }

        persistAll(order1)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderAddress::id))
                from(entity(OrderGroup::class))
                join(OrderGroup::class, OrderAddress::class, on(OrderGroup::address))
            }
        }

        // then
        assertThat(query)
            .hasSize(1)
            .containsOnly(address1.id)
    }

    @Test
    fun joinOneToMany() = runBlocking {
        // given
        val orderItem1 = orderItem { productId = 1000 }
        val orderItem2 = orderItem { productId = 2000 }

        val order1 = order { groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1, orderItem2) }) }

        persistAll(order1)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::productId))
                from(entity(OrderGroup::class))
                join(OrderGroup::class, OrderItem::class, on(OrderGroup::items))
            }
        }

        // then
        assertThat(query)
            .hasSize(2)
            .containsOnly(1000, 2000)
    }

    @Test
    fun crossJoin() = runBlocking {
        // given
        val orderItem1 = orderItem { productId = 1000 }
        val orderItem2 = orderItem { productId = 2000 }

        val order1 = order { groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1, orderItem2) }) }

        persistAll(order1)

        val delivery1 = delivery { orderId = order1.id }

        persistAll(delivery1)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Delivery::id))
                from(entity(Order::class))
                join(Delivery::class, on { col(Delivery::orderId).equal(col(Order::id)) })
            }
        }

        // then
        assertThat(query)
            .hasSize(1)
            .containsOnly(delivery1.id)
    }

    @Test
    fun associateWithEmbedded() = runBlocking {
        // given
        val address1 = orderAddress { zipCode = "15022" }
        val group1 = orderGroup { address = address1 }
        val order1 = order { groups = hashSetOf(group1) }

        persistAll(order1)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<String> {
                select(col(Address::zipCode))
                from(entity(OrderAddress::class))
                associate(OrderAddress::class, Address::class, on(OrderAddress::address))
            }
        }

        // then
        assertThat(query)
            .hasSize(1)
            .containsOnly("15022")
    }
}

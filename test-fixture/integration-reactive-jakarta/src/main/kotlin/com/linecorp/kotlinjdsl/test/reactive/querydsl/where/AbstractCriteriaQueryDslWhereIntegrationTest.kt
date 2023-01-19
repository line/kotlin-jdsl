package com.linecorp.kotlinjdsl.test.reactive.querydsl.where

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.subquery
import com.linecorp.kotlinjdsl.test.entity.Address
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

abstract class AbstractCriteriaQueryDslWhereIntegrationTest<S> : CriteriaQueryDslIntegrationTest<S> {
    private val orderItem1 = orderItem { }
    private val orderItem2 = orderItem { }

    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 2000 }
    private val order3 = order {
        purchaserId = 3000
        groups = hashSetOf(orderGroup {
            items = hashSetOf(orderItem1, orderItem2)
            address = orderAddress {
                zipCode = "zipCode1"
            }
        })
    }

    @BeforeEach
    fun setUp() = runBlocking {
        persistAll(order1, order2, order3)
    }

    @Test
    fun where() = runBlocking {
        // when
        val order = withFactory { queryFactory ->
            queryFactory.singleQuery<Order> {
                select(entity(Order::class))
                from(entity(Order::class))
                where(col(Order::purchaserId).equal(1000))
                fetch(Order::groups)
                fetch(OrderGroup::items)
                fetch(OrderGroup::address)
            }
        }

        // then
        assertThat(order)
            .usingRecursiveComparison()
            .withComparatorForType(BigDecimal::compareTo, BigDecimal::class.java)
            .isEqualTo(order1)
    }

    @Test
    fun `where using subquery`() = runBlocking {
        // when
        val orderIds = withFactory { queryFactory ->
            val subquery = queryFactory.subquery {
                val order = entity(Order::class, "o")

                select(col(order, Order::id))
                from(order)
                where(col(order, Order::purchaserId).lessThan(2000))
            }
            queryFactory.listQuery<Long> {
                select(col(Order::id))
                from(entity(Order::class))
                where(col(Order::id).`in`(subquery))
            }
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order1.id))
    }

    @Test
    fun `where using subquery with ref key`() = runBlocking {
        // when
        val orderIds = withFactory { queryFactory ->
            val subQuery = queryFactory.subquery {
                select(nestedCol(col(OrderGroup::order), Order::id))
                from(entity(OrderGroup::class))
                join(OrderGroup::address)
                associate(OrderAddress::class, Address::class, on(OrderAddress::address))
                where(col(Address::zipCode).equal("zipCode1"))
            }

            queryFactory.listQuery<Long> {
                select(col(Order::id))
                from(entity(Order::class))
                where(col(Order::id).`in`(subQuery))
            }
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order3.id).sorted())
    }
}

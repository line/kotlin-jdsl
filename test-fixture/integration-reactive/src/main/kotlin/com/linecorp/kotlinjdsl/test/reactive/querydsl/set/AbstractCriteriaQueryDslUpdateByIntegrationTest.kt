package com.linecorp.kotlinjdsl.test.reactive.querydsl.set

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.entity.Address
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import com.linecorp.kotlinjdsl.updateQuery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslUpdateByIntegrationTest<S> : CriteriaQueryDslIntegrationTest<S> {
    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() = runBlocking {
        persistAll(order1, order2, order3)
    }

    @Test
    fun update() = runBlocking {
        // when
        val purchaserIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                groupBy(col(Order::purchaserId))
            }
        }

        assertThat(purchaserIds).containsOnly(1000, 2000)

        withFactory { queryFactory ->
            queryFactory.updateQuery<Order> {
                where(col(Order::purchaserId).`in`(1000, 2000))
                setParams(col(Order::purchaserId) to 3000)
            }.executeUpdate
        }

        // then
        assertThat(withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                groupBy(col(Order::purchaserId))
            }
        }).containsOnly(3000)
    }

    @Test
    fun updateEmbedded() = runBlocking {
        // given
        val address1 = orderAddress { }
        val group1 = orderGroup { address = address1 }
        val order1 = order { groups = hashSetOf(group1) }

        persistAll(order1)

        withFactory { queryFactory ->
            queryFactory.updateQuery<OrderAddress> {
                where(col(OrderAddress::id).equal(address1.id))
                associate(OrderAddress::class, Address::class, on(OrderAddress::address))
                set(col(Address::zipCode), "test")
                set(col(Address::baseAddress), "base")
            }.executeUpdate
        }

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<OrderGroup> {
                select(entity(OrderGroup::class))
                from(entity(OrderGroup::class))
                where(col(OrderAddress::id).equal(address1.id))
                fetch(OrderGroup::class, OrderAddress::class, on(OrderGroup::address))
            }
        }

        // then
        query.map { it.address }.first().address.apply {
            assertThat(zipCode).isEqualTo("test")
            assertThat(baseAddress).isEqualTo("base")
        }

    }
}

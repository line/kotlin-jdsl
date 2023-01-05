package com.linecorp.kotlinjdsl.test.integration.querydsl.set

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.selectQuery
import com.linecorp.kotlinjdsl.test.entity.Address
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.updateQuery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslUpdateByIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(order1, order2, order3)
        entityManager.flushAndClear()
    }

    @Test
    fun update() {
        // when
        val purchaserIds = queryFactory.listQuery {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            groupBy(col(Order::purchaserId))
        }

        assertThat(purchaserIds).containsOnly(1000, 2000)

        queryFactory.updateQuery<Order> {
            where(col(Order::purchaserId).`in`(1000L, 2000L))
            setParams(col(Order::purchaserId) to 3000L)
        }.executeUpdate()

        // then
        assertThat(queryFactory.listQuery<Long> {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            groupBy(col(Order::purchaserId))
        }).containsOnly(3000)
    }

    @Test
    fun updateEmbedded() {
        // given
        val address1 = orderAddress { }
        val group1 = orderGroup { address = address1 }
        val order1 = order { groups = hashSetOf(group1) }

        entityManager.run {
            persistAll(order1)
            flushAndClear()
        }

        queryFactory.updateQuery<OrderAddress> {
            where(col(OrderAddress::id).equal(address1.id))
            associate(OrderAddress::class, Address::class, on(OrderAddress::address))
            set(col(Address::zipCode), "test")
            set(col(Address::baseAddress), "base")
        }.executeUpdate()

        // when
        val query = queryFactory.selectQuery<OrderGroup> {
            select(entity(OrderGroup::class))
            from(entity(OrderGroup::class))
            where(col(OrderAddress::id).equal(address1.id))
            fetch(OrderGroup::class, OrderAddress::class, on(OrderGroup::address))
        }

        // then
        query.resultList.map { it.address }.first().address.apply {
            assertThat(zipCode).isEqualTo("test")
            assertThat(baseAddress).isEqualTo("base")
        }

    }

    @Test
    fun updateByRefKey() {
        // given
        val orderItem1 = orderItem { }
        val order1 = order {
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1) })
        }

        entityManager.run {
            persistAll(order1)
            flushAndClear()
        }

        // when
        queryFactory.updateQuery<OrderGroup> {
            where(nestedCol(col(OrderGroup::order), Order::id).`in`(order1.id))
            setParams(col(OrderGroup::orderGroupName) to "newGroupName")
        }.executeUpdate()

        // then
        assertThat(queryFactory.listQuery {
            select(col(OrderGroup::orderGroupName))
            from(entity(OrderGroup::class))
            join(OrderGroup::order)
            where(col(Order::id).equal(order1.id))
        }).containsOnly("newGroupName")

        assertThat(queryFactory.listQuery {
            select(col(OrderGroup::orderGroupName))
            from(entity(OrderGroup::class))
            join(OrderGroup::order)
            where(col(Order::id).notEqual(order1.id))
        }).containsOnly("orderGroupName")
    }
}

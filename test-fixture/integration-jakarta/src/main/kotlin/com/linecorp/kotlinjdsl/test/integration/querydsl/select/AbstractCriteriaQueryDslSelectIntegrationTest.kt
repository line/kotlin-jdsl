package com.linecorp.kotlinjdsl.test.integration.querydsl.select

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.querydsl.expression.function
import com.linecorp.kotlinjdsl.querydsl.expression.max
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.subquery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

abstract class AbstractCriteriaQueryDslSelectIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val orderItem1 = orderItem { }
    private val orderItem2 = orderItem { }

    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order {
        purchaserId = 2000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1, orderItem2) })
    }

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(order1, order2, order3)
        entityManager.flushAndClear()
    }

    @Test
    fun `singleQuery - select single expression`() {
        // when
        val purchaserId = queryFactory.singleQuery<Long> {
            select(max(Order::id))
            from(entity(Order::class))
        }

        // then
        assertThat(purchaserId).isEqualTo(listOf(order1.id, order2.id, order3.id).maxOrNull())
    }

    @Test
    fun `listQuery - select single entity`() {
        // when
        val orders = queryFactory.listQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
        }

        // then
        assertThat(orders).isEqualTo(orders)
    }

    @Test
    fun `listQuery - select single column`() {
        // when
        val orderIds = queryFactory.listQuery<Long> {
            select(col(Order::id))
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order1.id, order2.id, order3.id).sorted())
    }

    @Test
    fun `listQuery - select single expression`() {
        // when
        val counts = queryFactory.listQuery<Long> {
            select(count(Order::id))
            from(entity(Order::class))
            groupBy(col(Order::purchaserId))
        }

        // then
        assertThat(counts).isEqualTo(listOf(2L, 1L))
    }

    @Test
    fun `listQuery - select distinct single column`() {
        val purchaserIds = queryFactory.listQuery<Long> {
            selectDistinct(col(Order::purchaserId))
            from(entity(Order::class))
            orderBy(col(Order::purchaserId).asc())
        }

        // then
        assertThat(purchaserIds).isEqualTo(listOf(1000L, 2000L))
    }

    /**
     * Currently, in the case of this test, eclipselink does not support it properly,
     * so for tests that generate an exception like this, open it so that it can be extended with open.
     */
    @Test
    open fun `listQuery - subquery in select, subquery in from`() {
        val subquery = queryFactory.subquery {
            val order = entity(Order::class, "o")

            select(count(col(order, Order::id)))
            from(order)
            where(col(order, Order::purchaserId).equal(col(Order::purchaserId)))
        }

        val counts = queryFactory.listQuery {
            select(subquery)
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
        }

        // then
        assertThat(counts).isEqualTo(listOf(2L, 2L, 1L))
    }

    @Test
    fun `function - sqrt function, single parameter`() {
        val purchaserIdSquareRoot = queryFactory.singleQuery<Double> {
            select(function("sqrt", col(Order::purchaserId)))
            from(entity(Order::class))
            where(col(Order::purchaserId).equal(order1.purchaserId))
            maxResults(1)
        }

        // then
        assertThat(purchaserIdSquareRoot).isEqualTo(sqrt(order1.purchaserId.toDouble()))
    }

    @Test
    fun `function - substring function, multiple parameters`() {
        val result = queryFactory.singleQuery<String> {
            select(function("substring", col(OrderItem::productName), literal(1), literal(2)))
            from(entity(OrderItem::class))
            where(col(OrderItem::id).equal(order1.groups.first().items.first().id))
        }

        // then
        assertThat(result).isEqualTo(order1.groups.first().items.first().productName.take(2))
    }

    @Test
    fun `nestedCol - ref key fetch`() {
        val result = queryFactory.listQuery {
            select(nestedCol(col(OrderGroup::order), Order::id))
            from(entity(OrderGroup::class))
            orderBy(nestedCol(col(OrderGroup::order), Order::id).asc())
        }

        // then
        assertThat(result).isEqualTo(listOf(order1.id, order2.id, order3.id).sorted())
    }

    @Test
    fun `nestedCol - ref key fetch nested function`() {
        val result = queryFactory.listQuery {
            select(col(OrderGroup::order).nested(Order::id))
            from(entity(OrderGroup::class))
            orderBy(nestedCol(col(OrderGroup::order), Order::id).asc())
        }

        // then
        assertThat(result).isEqualTo(listOf(order1.id, order2.id, order3.id).sorted())
    }

    @Test
    fun `nestedCol - implicit join and fetch column value`() {
        // when
        val result = queryFactory.listQuery {
            select(nestedCol(col(OrderGroup::order), Order::purchaserId))
            from(entity(OrderGroup::class))
            join(OrderGroup::address)
            orderBy(nestedCol(col(OrderGroup::order), Order::purchaserId).asc())
        }

        // then
        assertThat(result).isEqualTo(listOf(order1.purchaserId, order2.purchaserId, order3.purchaserId).sorted())
    }
}

package com.linecorp.kotlinjdsl.test.reactive.querydsl.select

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.querydsl.expression.function
import com.linecorp.kotlinjdsl.querydsl.expression.max
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.subquery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import kotlinx.coroutines.future.await
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

abstract class AbstractCriteriaQueryDslSelectIntegrationTest<S> : CriteriaQueryDslIntegrationTest<S> {
    private val orderItem1 = orderItem { }
    private val orderItem2 = orderItem { }

    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order {
        purchaserId = 2000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1, orderItem2) })
    }

    @BeforeEach
    fun setUp() = runBlocking {
        persistAll(order1, order2, order3)
    }

    @Test
    fun `singleQuery - select single expression`() = runBlocking {
        // when
        val purchaserId = withFactory { queryFactory ->
            queryFactory.singleQuery<Long> {
                select(max(Order::id))
                from(entity(Order::class))
            }.await()
        }

        // then
        assertThat(purchaserId).isEqualTo(listOf(order1.id, order2.id, order3.id).maxOrNull())
    }

    @Test
    fun `listQuery - select single entity`() = runBlocking {
        // when
        val orders = withFactory { queryFactory ->
            queryFactory.listQuery<Order> {
                select(entity(Order::class))
                from(entity(Order::class))
                orderBy(col(Order::id).asc())
            }.await()
        }

        // then
        assertThat(orders).isEqualTo(orders)
    }

    @Test
    fun `listQuery - select single column`() = runBlocking {
        // when
        val orderIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::id))
                from(entity(Order::class))
                orderBy(col(Order::id).asc())
            }.await()
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order1.id, order2.id, order3.id).sorted())
    }

    @Test
    fun `listQuery - select single expression`() = runBlocking {
        // when
        val counts = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(count(Order::id))
                from(entity(Order::class))
                groupBy(col(Order::purchaserId))
            }.await()
        }

        // then
        assertThat(counts).isEqualTo(listOf(2L, 1L))
    }

    @Test
    fun `listQuery - select distinct single column`() = runBlocking {
        val purchaserIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                selectDistinct(col(Order::purchaserId))
                from(entity(Order::class))
                orderBy(col(Order::purchaserId).asc())
            }.await()
        }

        // then
        assertThat(purchaserIds).isEqualTo(listOf(1000L, 2000L))
    }

    /**
     * Currently, in the case of this test, eclipselink does not support it properly,
     * so for tests that generate an exception like this, open it so that it can be extended with open.
     */
    @Test
    open fun `listQuery - subquery in select, subquery in from`() = runBlocking {
        val counts = withFactory { queryFactory ->
            val subquery = queryFactory.subquery<Long> {
                val order = entity(Order::class, "o")

                select(count(col(order, Order::id)))
                from(order)
                where(col(order, Order::purchaserId).equal(col(Order::purchaserId)))
            }
            queryFactory.listQuery<Long> {
                select(subquery)
                from(entity(Order::class))
                orderBy(col(Order::id).asc())
            }.await()
        }

        // then
        assertThat(counts).isEqualTo(listOf(2L, 2L, 1L))
    }

    @Test
    fun `function - sqrt function, single parameter`() = runBlocking {
        val purchaserIdSquareRoot = withFactory { queryFactory ->
            queryFactory.singleQuery<Double> {
                select(function("sqrt", col(Order::purchaserId)))
                from(entity(Order::class))
                where(col(Order::purchaserId).equal(order1.purchaserId))
                maxResults(1)
            }.await()
        }

        // then
        assertThat(purchaserIdSquareRoot).isEqualTo(sqrt(order1.purchaserId.toDouble()))
    }

    @Test
    fun `function - substring function, mutliple parameters`() = runBlocking {
        val result = withFactory { queryFactory ->
            queryFactory.singleQuery<String> {
                select(function("substring", col(OrderItem::productName), literal(1), literal(2)))
                from(entity(OrderItem::class))
                where(col(OrderItem::id).equal(order1.groups.first().items.first().id))
            }.await()
        }

        // then
        assertThat(result).isEqualTo(order1.groups.first().items.first().productName.take(2))
    }
}

package com.linecorp.kotlinjdsl.test.reactive.querydsl.predicate

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslPredicateIntegrationTest<S> : CriteriaQueryDslIntegrationTest<S> {
    private val orderItem1 = orderItem { productName = "test1"; productImage = null; price = 10; claimed = true }
    private val orderItem2 = orderItem { productName = "test1"; productImage = null; price = 20; claimed = false }
    private val orderItem3 = orderItem { productName = "test2"; productImage = null; price = 30; claimed = false }
    private val orderItem4 = orderItem { productName = "test3"; productImage = "image"; price = 50; claimed = false }

    private val order1 = order {
        purchaserId = 1000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1, orderItem2) })
    }
    private val order2 = order {
        purchaserId = 1000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem3) })
    }
    private val order3 = order {
        purchaserId = 2000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem4) })
    }

    private val orders = listOf(order1, order2, order3)

    @BeforeEach
    fun setUp() = runBlocking {
        persistAll(order1, order2, order3)

    }

    @Test
    fun not() = runBlocking {
        // when
        val orderIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::id))
                from(entity(Order::class))
                where(not(col(Order::purchaserId).equal(1000)))
                orderBy(col(Order::id).asc())
            }
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order3.id))
    }

    @Test
    fun and() = runBlocking {
        // when
        val orderItemId = withFactory { queryFactory ->
            queryFactory.singleQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(
                    and(
                        col(OrderItem::productName).equal("test1"),
                        col(OrderItem::price).equal(10.toBigDecimal())
                    )
                )
            }
        }

        // then
        assertThat(orderItemId).isEqualTo(orderItem1.id)
    }

    @Test
    fun or() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(
                    or(
                        col(OrderItem::price).equal(10.toBigDecimal()),
                        col(OrderItem::price).equal(20.toBigDecimal())
                    )
                )
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id)
    }

    @Test
    fun equal() = runBlocking {
        // when
        val orderItemId = withFactory { queryFactory ->
            queryFactory.singleQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::id).equal(orderItem1.id))
            }
        }

        // then
        assertThat(orderItemId).isEqualTo(orderItem1.id)
    }

    @Test
    fun notEqual() = runBlocking {
        // when
        val orderItemId = withFactory { queryFactory ->
            queryFactory.singleQuery<Long> {
                select(col(Order::id))
                from(entity(Order::class))
                where(col(Order::purchaserId).notEqual(1000))
            }
        }

        // then
        assertThat(orderItemId).isEqualTo(order3.id)
    }

    @Test
    fun `in`() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::id).`in`(orderItem1.id, orderItem2.id))
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id)
    }

    @Test
    fun lessThanOrEqualTo() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::price).lessThanOrEqualTo(20.toBigDecimal()))
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id)
    }

    @Test
    fun lessThan() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::price).lessThan(20.toBigDecimal()))
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id)
    }

    @Test
    fun greaterThanOrEqualTo() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::price).greaterThanOrEqualTo(20.toBigDecimal()))
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem2.id, orderItem3.id, orderItem4.id)
    }

    @Test
    fun greaterThan() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::price).greaterThan(20.toBigDecimal()))
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem3.id, orderItem4.id)
    }

    @Test
    fun between() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::price).between(20.toBigDecimal(), 40.toBigDecimal()))
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem2.id, orderItem3.id)
    }

    @Test
    fun isTrue() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::claimed).isTrue())
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id)
    }

    @Test
    fun isFalse() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::claimed).isFalse())
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem2.id, orderItem3.id, orderItem4.id)
    }

    @Test
    fun isNull() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::productImage).isNull())
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id, orderItem3.id)
    }

    @Test
    fun isNotNull() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::productImage).isNotNull())
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem4.id)
    }

    @Test
    fun like() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::productName).like("test%"))
            }
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id, orderItem3.id, orderItem4.id)
    }

    @Test
    fun notLike() = runBlocking {
        // when
        val orderItemIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(OrderItem::id))
                from(entity(OrderItem::class))
                where(col(OrderItem::productName).notLike("test%"))
            }
        }

        // then
        assertThat(orderItemIds).isEmpty()
    }
}

package com.linecorp.kotlinjdsl.test.integration.querydsl.predicate

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.subquery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslPredicateIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val orderItem1 = orderItem { productName = "test1"; productImage = null; price = 10; claimed = true }
    private val orderItem2 = orderItem { productName = "test1"; productImage = null; price = 20; claimed = false }
    private val orderItem3 = orderItem { productName = "test2"; productImage = null; price = 30; claimed = false }
    private val orderItem4 = orderItem { productName = "test3"; productImage = "image"; price = 50; claimed = false }

    private val order1 = order {
        purchaserId = 1000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1, orderItem2); orderGroupName = "orderGroup1" })
    }
    private val order2 = order {
        purchaserId = 1000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem3); orderGroupName = "orderGroup2" })
    }
    private val order3 = order {
        purchaserId = 2000
        groups = hashSetOf(orderGroup { items = hashSetOf(orderItem4); orderGroupName = "orderGroup3" })
    }

    private val orders = listOf(order1, order2, order3)

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(orders)
        entityManager.flushAndClear()
    }

    @Test
    fun not() {
        // when
        val orderIds = queryFactory.listQuery {
            select(col(Order::id))
            from(entity(Order::class))
            where(not(col(Order::purchaserId).equal(1000)))
            orderBy(col(Order::id).asc())
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order3.id))
    }

    @Test
    fun and() {
        // when
        val orderItemId = queryFactory.singleQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(
                and(
                    col(OrderItem::productName).equal("test1"),
                    col(OrderItem::price).equal(10.toBigDecimal())
                )
            )
        }

        // then
        assertThat(orderItemId).isEqualTo(orderItem1.id)
    }

    @Test
    fun or() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(
                or(
                    col(OrderItem::price).equal(10.toBigDecimal()),
                    col(OrderItem::price).equal(20.toBigDecimal())
                )
            )
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id)
    }

    @Test
    fun equal() {
        // when
        val orderItemId = queryFactory.singleQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::id).equal(orderItem1.id))
        }

        // then
        assertThat(orderItemId).isEqualTo(orderItem1.id)
    }

    @Test
    fun notEqual() {
        // when
        val orderItemId = queryFactory.singleQuery {
            select(col(Order::id))
            from(entity(Order::class))
            where(col(Order::purchaserId).notEqual(1000))
        }

        // then
        assertThat(orderItemId).isEqualTo(order3.id)
    }

    @Test
    fun `in`() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::id).`in`(orderItem1.id, orderItem2.id))
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id)
    }

    @Test
    fun lessThanOrEqualTo() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::price).lessThanOrEqualTo(20.toBigDecimal()))
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id)
    }

    @Test
    fun lessThan() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::price).lessThan(20.toBigDecimal()))
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id)
    }

    @Test
    fun greaterThanOrEqualTo() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::price).greaterThanOrEqualTo(20.toBigDecimal()))
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem2.id, orderItem3.id, orderItem4.id)
    }

    @Test
    fun greaterThan() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::price).greaterThan(20.toBigDecimal()))
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem3.id, orderItem4.id)
    }

    @Test
    fun between() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::price).between(20.toBigDecimal(), 40.toBigDecimal()))
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem2.id, orderItem3.id)
    }

    @Test
    fun isTrue() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::claimed).isTrue())
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id)
    }

    @Test
    fun isFalse() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::claimed).isFalse())
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem2.id, orderItem3.id, orderItem4.id)
    }

    @Test
    fun isNull() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::productImage).isNull())
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id, orderItem3.id)
    }

    @Test
    fun isNotNull() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::productImage).isNotNull())
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem4.id)
    }

    @Test
    fun like() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::productName).like("test%"))
        }

        // then
        assertThat(orderItemIds).containsOnly(orderItem1.id, orderItem2.id, orderItem3.id, orderItem4.id)
    }

    @Test
    fun notLike() {
        // when
        val orderItemIds = queryFactory.listQuery {
            select(col(OrderItem::id))
            from(entity(OrderItem::class))
            where(col(OrderItem::productName).notLike("test%"))
        }

        // then
        assertThat(orderItemIds).isEmpty()
    }

    @Test
    fun exists() {
        // when
        val existFoundOrders = queryFactory.listQuery {
            val entity: EntitySpec<Order> = entity(Order::class)
            select(entity)
            from(entity)
            where(
                exists(queryFactory.subquery<Long> {
                    val orderGroupEntity = entity(OrderGroup::class)
                    select(literal(1))
                    from(orderGroupEntity)
                    where(
                        and(
                            col(OrderGroup::orderGroupName).equal("orderGroup1"),
                            col(OrderGroup::order).equal(entity)
                        )
                    )
                })
            )
        }

        // then
        assertThat(existFoundOrders.map { it.id }).isEqualTo(listOf(order1.id))
    }

    @Test
    fun notExists() {
        // when
        val existFoundOrders = queryFactory.listQuery {
            val entity: EntitySpec<Order> = entity(Order::class)
            select(entity)
            from(entity)
            where(
                notExists(queryFactory.subquery<Long> {
                    val orderGroupEntity = entity(OrderGroup::class)
                    select(literal(1))
                    from(orderGroupEntity)
                    where(
                        and(
                            col(OrderGroup::orderGroupName).equal("orderGroup1"),
                            col(OrderGroup::order).equal(entity)
                        )
                    )
                })
            )
        }

        // then
        assertThat(existFoundOrders.map { it.id }).isEqualTo(listOf(order2.id, order3.id))
    }
}

package com.linecorp.kotlinjdsl.test.integration.querydsl.expression

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.*
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

abstract class AbstractCriteriaQueryDslExpressionIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
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
    fun setUp() {
        entityManager.persistAll(orders)
        entityManager.flushAndClear()
    }

    @Test
    fun entity() {
        // when
        val orders = queryFactory.listQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
        }

        // then
        assertThat(orders.map { it.id }).containsOnly(order1.id, order2.id, order3.id)
    }

    @Test
    fun literal() {
        // when
        val literals = queryFactory.listQuery<Int> {
            select(literal(10))
            from(entity(Order::class))
        }

        // then
        assertThat(literals).containsOnly(10, 10, 10, 10)
    }

    @Test
    fun nullLiteral() {
        // when
        val literals = queryFactory.listQuery<Int?> {
            select(nullLiteral(Int::class.java))
            from(entity(Order::class))
        }

        // then
        assertThat(literals).containsOnly(null, null, null, null)
    }

    @Test
    fun column() {
        // when
        val literals = queryFactory.listQuery<Long> {
            select(column(Order::id))
            from(entity(Order::class))
        }

        // then
        assertThat(literals).containsOnly(order1.id, order2.id, order3.id)
    }

    @Test
    fun max() {
        // when
        val max = queryFactory.singleQuery<BigDecimal> {
            select(max(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(max).isEqualByComparingTo(50.toString())
    }

    @Test
    fun min() {
        // when
        val min = queryFactory.singleQuery<BigDecimal> {
            select(min(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(min).isEqualByComparingTo(10.toString())
    }

    @Test
    fun avg() {
        // when
        val avg = queryFactory.singleQuery<Double> {
            select(avg(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(avg).isEqualTo(27.5)
    }

    @Test
    fun sum() {
        // when
        val sum = queryFactory.singleQuery<BigDecimal> {
            select(sum(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(sum).isEqualByComparingTo(110.toString())
    }

    @Test
    fun count() {
        // when
        val count = queryFactory.singleQuery<Long> {
            select(count(OrderItem::id))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(count).isEqualTo(4)
    }

    @Test
    fun countDistinct() {
        // when
        val count = queryFactory.singleQuery<Long> {
            select(countDistinct(OrderItem::productName))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(count).isEqualTo(3)
    }

    @Test
    fun greatest() {
        // when
        val greatest = queryFactory.singleQuery<String> {
            select(greatest(OrderItem::productName))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(greatest).isEqualTo("test3")
    }

    @Test
    fun least() {
        // when
        val least = queryFactory.singleQuery<String> {
            select(least(OrderItem::productName))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(least).isEqualTo("test1")
    }

    @Test
    fun caseWhen() {
        // when
        val values = queryFactory.listQuery<Int?> {
            select(
                case(
                    `when`(col(OrderItem::productName).equal("test1")).then(literal(1)),
                    `when`(col(OrderItem::productName).equal("test2")).then(literal(2)),
                    `else` = nullLiteral()
                )
            )
            from(entity(OrderItem::class))
            orderBy(col(OrderItem::productName).asc())
        }

        // then
        assertThat(values).isEqualTo(listOf(1, 1, 2, null))
    }
}

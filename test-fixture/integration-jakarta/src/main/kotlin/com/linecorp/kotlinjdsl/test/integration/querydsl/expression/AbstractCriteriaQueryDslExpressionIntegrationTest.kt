package com.linecorp.kotlinjdsl.test.integration.querydsl.expression

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.*
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.entity.test.TestTable
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
    fun entityAlias() {
        // when
        val orders = queryFactory.listQuery {
            val entity = entity(Order::class, "orderAlias")
            select(entity)
            from(entity)
        }

        // then
        assertThat(orders.map { it.id }).containsOnly(order1.id, order2.id, order3.id)
    }

    @Test
    fun literal() {
        // when
        val literals = queryFactory.listQuery {
            select(literal(10))
            from(entity(Order::class))
        }

        // then
        assertThat(literals).containsOnly(10, 10, 10, 10)
    }

    @Test
    fun nullLiteral() {
        // when
        val literals = queryFactory.listQuery {
            select(nullLiteral(Int::class.java))
            from(entity(Order::class))
        }

        // then
        assertThat(literals).containsOnly(null, null, null, null)
    }

    @Test
    fun column() {
        // when
        val literals = queryFactory.listQuery {
            select(column(Order::id))
            from(entity(Order::class))
        }

        // then
        assertThat(literals).containsOnly(order1.id, order2.id, order3.id)
    }

    @Test
    fun max() {
        // when
        val max = queryFactory.singleQuery {
            select(max(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(max).isEqualByComparingTo(50.toString())
    }

    @Test
    fun min() {
        // when
        val min = queryFactory.singleQuery {
            select(min(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(min).isEqualByComparingTo(10.toString())
    }

    @Test
    fun avg() {
        // when
        val avg = queryFactory.singleQuery {
            select(avg(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(avg).isEqualTo(27.5)
    }

    @Test
    fun sum() {
        // when
        val sum = queryFactory.singleQuery {
            select(sum(OrderItem::price))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(sum).isEqualByComparingTo(110.toString())
    }

    @Test
    fun sumWhen() {
        // when
        data class DataDTO(
            val id: Long,
            val amount: Long
        )

        entityManager.persist(TestTable(id = 1, role = "A", occurAmount = 7))
        entityManager.persist(TestTable(id = 2, role = "A", occurAmount = 5))
        entityManager.persist(TestTable(id = 1, role = "B", occurAmount = 5))
        entityManager.persist(TestTable(id = 2, role = "B", occurAmount = 6))
        entityManager.persist(TestTable(id = 3, role = "C", occurAmount = 6))
        entityManager.flush()

        val sum = queryFactory.listQuery<DataDTO> {
            selectMulti(
                col(TestTable::id),
                sum(
                    case(
                        `when`(col(TestTable::role).`in`("A", "B")).then(col(TestTable::occurAmount)),
                        `else` = literal(0L)
                    )
                ),
            )
            from(entity(TestTable::class))
            groupBy(
                col(TestTable::id),
            )
        }

        // then
        assertThat(sum.first { it.id == 1L }.amount).isEqualTo(12L)
        assertThat(sum.first { it.id == 2L }.amount).isEqualTo(11L)
        assertThat(sum.first { it.id == 3L }.amount).isEqualTo(0L)
    }

    @Test
    fun count() {
        // when
        val count = queryFactory.singleQuery {
            select(count(OrderItem::id))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(count).isEqualTo(4)
    }

    @Test
    fun countExpression() {
        // when
        val count = queryFactory.singleQuery {
            select(
                count(literal(1))
            )
            from(entity(OrderItem::class))
        }

        // then
        assertThat(count).isEqualTo(4L)
    }

    @Test
    fun countDistinct() {
        // when
        val count = queryFactory.singleQuery {
            select(countDistinct(OrderItem::productName))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(count).isEqualTo(3)
    }

    @Test
    fun greatest() {
        // when
        val greatest = queryFactory.singleQuery {
            select(greatest(OrderItem::productName))
            from(entity(OrderItem::class))
        }

        // then
        assertThat(greatest).isEqualTo("test3")
    }

    @Test
    fun least() {
        // when
        val least = queryFactory.singleQuery {
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

package com.linecorp.kotlinjdsl.test.integration.criteriaquery

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.typedQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class GroupByDslTest : AbstractCriteriaQueryDslIntegrationTest(), WithKotlinJdslAssertions {
    override lateinit var entityManager: EntityManager

    @Test
    fun groupBy() {
        // given
        data class Row(
            val purchaserId: Long,
            val quantity: Long,
        )

        val orderItem1 = orderItem { productId = 100; quantity = 5 }
        val orderItem2 = orderItem { productId = 200; quantity = 2 }
        val orderItem3 = orderItem { productId = 200; quantity = 1 }

        val order1 = order {
            purchaserId = 1000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1) })
        }

        val order2 = order {
            purchaserId = 2000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem2, orderItem3) })
        }

        entityManager.run {
            persist(order1)
            persist(order2)
            flush(); clear()
        }

        // when
        val query = queryFactory.typedQuery<Row> {
            selectMulti(col(Order::purchaserId), sum(col(OrderItem::quantity)))
            from(entity(Order::class))
            join(Order::groups)
            join(OrderGroup::items)
            groupBy(col(Order::purchaserId))
        }

        // then
        assertThat(query.resultList)
            .hasSize(2)
            .containsOnly(
                Row(purchaserId = 1000, quantity = 5),
                Row(purchaserId = 2000, quantity = 3),
            )
    }

    @Test
    fun having() {
        // given
        val orderItem1 = orderItem { productId = 100; quantity = 5 }
        val orderItem2 = orderItem { productId = 200; quantity = 2 }
        val orderItem3 = orderItem { productId = 200; quantity = 1 }

        val order1 = order {
            purchaserId = 1000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1) })
        }

        val order2 = order {
            purchaserId = 2000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem2, orderItem3) })
        }

        entityManager.run {
            persist(order1)
            persist(order2)
            flush(); clear()
        }

        // when
        val query = queryFactory.typedQuery<Long> {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            join(Order::groups)
            join(OrderGroup::items)
            groupBy(col(Order::purchaserId))
            having(sum(col(OrderItem::quantity)).greaterThan(4))
        }

        // then
        assertThat(query.resultList)
            .hasSize(1)
            .containsOnly(1000L)
    }
}
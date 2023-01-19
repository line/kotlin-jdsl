package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.junit.jupiter.api.Test

abstract class AbstractFetchDslTest<S> : CriteriaQueryDslIntegrationTest<S>, WithKotlinJdslAssertions {
    @Test
    fun fetchOneToOne() = runBlocking {
        // given
        val address1 = orderAddress { }
        val group1 = orderGroup { address = address1 }
        val order1 = order { groups = hashSetOf(group1) }

        persistAll(order1)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<OrderGroup> {
                select(entity(OrderGroup::class))
                from(entity(OrderGroup::class))
                fetch(OrderGroup::class, OrderAddress::class, on(OrderGroup::address))
            }
        }

        // then
        assertThat(query.map { it.address })
            .usingRecursiveFieldByFieldElementComparator()
            .containsOnly(address1)
    }

    @Test
    fun fetchOneToMany() = runBlocking {
        // given
        val orderItem1 = orderItem { productId = 1000 }
        val orderItem2 = orderItem { productId = 2000 }

        val group1 = orderGroup { items = hashSetOf(orderItem1, orderItem2) }
        val order1 = order { groups = hashSetOf(group1) }

        persistAll(order1)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<OrderGroup> {
                selectDistinct(entity(OrderGroup::class))
                from(entity(OrderGroup::class))
                fetch(OrderGroup::class, OrderItem::class, on(OrderGroup::items))
            }
        }

        // then
        assertThat(query.flatMap { it.items }.map { it.id })
            .hasSize(2)
            .containsOnly(orderItem1.id, orderItem2.id)
    }
}

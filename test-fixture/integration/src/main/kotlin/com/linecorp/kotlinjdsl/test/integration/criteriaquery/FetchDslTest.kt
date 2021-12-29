package com.linecorp.kotlinjdsl.test.integration.criteriaquery

import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.typedQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
class FetchDslTest : AbstractCriteriaQueryDslIntegrationTest(), WithKotlinJdslAssertions {
    override lateinit var entityManager: EntityManager

    @Test
    fun fetchOneToOne() {
        // given
        val address1 = orderAddress { }
        val group1 = orderGroup { address = address1 }
        val order1 = order { groups = hashSetOf(group1) }

        entityManager.run {
            persist(order1)
            flush(); clear()
        }

        // when
        val query = queryFactory.typedQuery<OrderGroup> {
            select(entity(OrderGroup::class))
            from(entity(OrderGroup::class))
            fetch(OrderGroup::class, OrderAddress::class, on(OrderGroup::address))
        }

        entityManager.detach(group1)

        // then
        assertThat(query.resultList.map { it.address })
            .usingRecursiveFieldByFieldElementComparator()
            .containsOnly(address1)
    }

    @Test
    fun fetchOneToMany() {
        // given
        val orderItem1 = orderItem { productId = 1000 }
        val orderItem2 = orderItem { productId = 2000 }

        val group1 = orderGroup { items = hashSetOf(orderItem1, orderItem2) }
        val order1 = order { groups = hashSetOf(group1) }

        entityManager.run {
            persist(order1)
            flush(); clear()
        }

        // when
        val query = queryFactory.typedQuery<OrderGroup> {
            selectDistinct(entity(OrderGroup::class))
            from(entity(OrderGroup::class))
            fetch(OrderGroup::class, OrderItem::class, on(OrderGroup::items))
        }

        entityManager.detach(group1)

        // then
        assertThat(query.resultList.flatMap { it.items }.map { it.id })
            .hasSize(2)
            .containsOnly(orderItem1.id, orderItem2.id)
    }
}
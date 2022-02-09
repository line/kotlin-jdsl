package com.linecorp.kotlinjdsl.test.integration.criteriaquery

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import com.linecorp.kotlinjdsl.selectQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
abstract class AbstractOrderByDslTest : AbstractCriteriaQueryDslIntegrationTest(), WithKotlinJdslAssertions {
    override lateinit var entityManager: EntityManager

    @Test
    fun `orderBy - asc`() {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }

        entityManager.run {
            persist(order1)
            persist(order2)
            flush(); clear()
        }

        // when
        val query = queryFactory.selectQuery<Long> {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            orderBy(col(Order::purchaserId).desc())
        }

        // then
        assertThat(query.resultList)
            .hasSize(2)
            .containsOnly(1000L, 2000L)
    }

    @Test
    fun `orderBy - desc`() {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }

        entityManager.run {
            persist(order1)
            persist(order2)
            flush(); clear()
        }

        // when
        val query = queryFactory.selectQuery<Long> {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            orderBy(col(Order::purchaserId).desc())
        }

        // then
        assertThat(query.resultList)
            .hasSize(2)
            .containsOnly(2000L, 1000L)
    }
}

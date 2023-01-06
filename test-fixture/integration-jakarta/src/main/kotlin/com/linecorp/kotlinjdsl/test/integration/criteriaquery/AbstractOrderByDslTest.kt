package com.linecorp.kotlinjdsl.test.integration.criteriaquery

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.selectQuery
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.Test

abstract class AbstractOrderByDslTest : AbstractCriteriaQueryDslIntegrationTest(), WithKotlinJdslAssertions {
    @Test
    fun `orderBy - asc`() {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }

        entityManager.run {
            persistAll(order1, order2)
            flushAndClear()
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
            persistAll(order1, order2)
            flushAndClear()
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

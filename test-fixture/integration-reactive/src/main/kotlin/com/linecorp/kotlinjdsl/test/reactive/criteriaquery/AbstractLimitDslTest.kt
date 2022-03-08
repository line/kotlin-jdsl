package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.junit.jupiter.api.Test

abstract class AbstractLimitDslTest<S> : CriteriaQueryDslIntegrationTest<S>, WithKotlinJdslAssertions {
    @Test
    fun offset() = runBlocking {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }
        val order3 = order { purchaserId = 3000 }

        persistAll(order1, order2, order3)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                orderBy(col(Order::purchaserId).asc())
                offset(1)
            }
        }

        // then
        assertThat(query).hasSize(2).containsOnly(2000L, 3000L)
    }

    @Test
    fun maxResults() = runBlocking {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }
        val order3 = order { purchaserId = 3000 }

        persistAll(order1, order2, order3)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                orderBy(col(Order::purchaserId).asc())
                maxResults(2)
            }
        }

        // then
        assertThat(query).hasSize(2).containsOnly(1000L, 2000L)
    }
}

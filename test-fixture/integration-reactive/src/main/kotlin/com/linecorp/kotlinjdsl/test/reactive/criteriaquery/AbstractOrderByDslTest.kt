package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import kotlinx.coroutines.future.await
import org.junit.jupiter.api.Test

abstract class AbstractOrderByDslTest<S> : CriteriaQueryDslIntegrationTest<S>, WithKotlinJdslAssertions {
    @Test
    fun `orderBy - asc`() = runBlocking {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }

        persistAll(order1, order2)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                orderBy(col(Order::purchaserId).desc())
            }.await()
        }

        // then
        assertThat(query)
            .hasSize(2)
            .containsOnly(1000L, 2000L)
    }

    @Test
    fun `orderBy - desc`() = runBlocking {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }

        persistAll(order1, order2)

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                orderBy(col(Order::purchaserId).desc())
            }.await()
        }

        // then
        assertThat(query)
            .hasSize(2)
            .containsOnly(2000L, 1000L)
    }
}

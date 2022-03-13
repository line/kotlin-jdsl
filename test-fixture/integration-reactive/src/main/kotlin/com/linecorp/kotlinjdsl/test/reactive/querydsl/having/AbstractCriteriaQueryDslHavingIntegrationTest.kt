package com.linecorp.kotlinjdsl.test.reactive.querydsl.having

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import kotlinx.coroutines.future.await
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslHavingIntegrationTest<S> : CriteriaQueryDslIntegrationTest<S> {
    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() = runBlocking {
        persistAll(order1, order2, order3)

    }

    @Test
    fun having() = runBlocking {
        // when
        val purchaserIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                groupBy(col(Order::purchaserId))
                having(count(Order::purchaserId).greaterThan(1))
            }.await()
        }

        // then
        assertThat(purchaserIds).containsOnly(1000)
    }
}

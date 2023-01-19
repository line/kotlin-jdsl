package com.linecorp.kotlinjdsl.test.reactive.querydsl.orderby

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaQueryDslOrderByIntegrationTest<S> : CriteriaQueryDslIntegrationTest<S> {
    private val order1 = order { }
    private val order2 = order { }

    @BeforeEach
    fun setUp() = runBlocking {
        persistAll(order1, order2)

    }

    @Test
    fun asc() = runBlocking {
        // when
        val orderIds = withFactory { queryFactory ->
            queryFactory.listQuery {
                select(col(Order::id))
                from(entity(Order::class))
                orderBy(col(Order::id).asc())
            }
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order1.id, order2.id).sorted())
    }

    @Test
    fun desc() = runBlocking {
        // when
        val orderIds = withFactory { queryFactory ->
            queryFactory.listQuery {
                select(col(Order::id))
                from(entity(Order::class))
                orderBy(col(Order::id).desc())
            }
        }

        // then
        assertThat(orderIds).isEqualTo(listOf(order2.id, order1.id).sortedDescending())
    }
}

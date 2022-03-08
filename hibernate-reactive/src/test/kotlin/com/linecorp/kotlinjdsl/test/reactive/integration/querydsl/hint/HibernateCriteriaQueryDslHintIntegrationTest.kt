package com.linecorp.kotlinjdsl.test.reactive.integration.querydsl.hint

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.min
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.hibernate.reactive.stage.Stage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class HibernateCriteriaQueryDslHintIntegrationTest :
    com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest {
    override lateinit var factory: Stage.SessionFactory
    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() = runBlocking {
        persistAll(order1, order2, order3)
    }

    @Test
    fun sqlHint() = runBlocking {
        // when
        val purchaserIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(min(Order::purchaserId))
                from(entity(Order::class))
                // Hibernate query hint handler inject query hints only when query has where clause
                // See org.hibernate.dialect.hint.IndexQueryHintHandler
                where(col(Order::id).`in`(order1.id, order2.id, order3.id))
                sqlHints("idx1")
            }
        }

        // then
        assertThat(purchaserIds).containsOnly(1000)
    }

    @Test
    fun jpaHint() = runBlocking {
        // when
        val purchaserIds = withFactory { queryFactory ->
            queryFactory.listQuery<Long> {
                select(col(Order::purchaserId))
                from(entity(Order::class))
                hints("org.hibernate.comment" to "comment")
            }
        }

        // then
        assertThat(purchaserIds).containsOnly(1000, 2000)
    }
}

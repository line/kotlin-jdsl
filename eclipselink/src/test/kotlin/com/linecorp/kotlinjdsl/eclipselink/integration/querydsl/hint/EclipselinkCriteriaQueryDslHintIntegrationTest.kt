package com.linecorp.kotlinjdsl.eclipselink.integration.querydsl.hint

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.min
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.eclipse.persistence.config.PessimisticLock
import org.eclipse.persistence.config.QueryHints
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EclipselinkCriteriaQueryDslHintIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {

    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(order1, order2, order3)
        entityManager.flushAndClear()
    }

    @Test
    fun sqlHint() {
        // when
        val purchaserIds = queryFactory.listQuery<Long> {
            select(min(Order::purchaserId))
            from(entity(Order::class))
            where(col(Order::id).`in`(order1.id, order2.id, order3.id))
            // Since eclipselink does not put /*+ */ comments automatically in SQL hints, users must put all comments in hints.
            sqlHints("/*+ idx1 */")
        }

        // then
        assertThat(purchaserIds).containsOnly(1000)
    }

    @Test
    fun jpaHint() {
        // when
        val purchaserIds = queryFactory.listQuery<Long> {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            hints(QueryHints.PESSIMISTIC_LOCK to PessimisticLock.LockNoWait)
        }

        // then
        assertThat(purchaserIds).containsOnly(1000, 2000)
    }
}

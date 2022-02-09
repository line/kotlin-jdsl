package com.linecorp.kotlinjdsl.test.integration.criteriaquery

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.selectQuery
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.integration.EntityManagerExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager

@ExtendWith(EntityManagerExtension::class)
abstract class AbstractLimitDslTest : AbstractCriteriaQueryDslIntegrationTest(), WithKotlinJdslAssertions {
    override lateinit var entityManager: EntityManager

    @Test
    fun offset() {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }
        val order3 = order { purchaserId = 3000 }

        entityManager.run {
            persist(order1)
            persist(order2)
            persist(order3)
            flush(); clear()
        }

        // when
        val query = queryFactory.selectQuery<Long> {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            orderBy(col(Order::purchaserId).asc())
            offset(1)
        }

        // then
        assertThat(query.resultList)
            .hasSize(2)
            .containsOnly(2000L, 3000L)
    }

    @Test
    fun maxResults() {
        // given
        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 2000 }
        val order3 = order { purchaserId = 3000 }

        entityManager.run {
            persist(order1)
            persist(order2)
            persist(order3)
            flush(); clear()
        }

        // when
        val query = queryFactory.selectQuery<Long> {
            select(col(Order::purchaserId))
            from(entity(Order::class))
            orderBy(col(Order::purchaserId).asc())
            maxResults(2)
        }

        // then
        assertThat(query.resultList)
            .hasSize(2)
            .containsOnly(1000L, 2000L)
    }
}

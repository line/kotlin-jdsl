package com.linecorp.kotlinjdsl.test.integration.criteriaquery

import com.linecorp.kotlinjdsl.deleteQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.selectQuery
import com.linecorp.kotlinjdsl.test.entity.Address
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaDeleteIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    @Test
    fun delete() {
        // when
        val address1 = orderAddress { }

        entityManager.run {
            persist(address1)
            flush(); clear()
        }

        queryFactory.deleteQuery<OrderAddress> {
            where(col(OrderAddress::id).equal(address1.id))
        }.executeUpdate()

        // when
        val query = queryFactory.selectQuery<OrderAddress> {
            select(entity(OrderAddress::class))
            from(entity(OrderAddress::class))
            where(col(OrderAddress::id).equal(address1.id),)
            associate(OrderAddress::class, Address::class, on(OrderAddress::address))
        }

        // then
        assertThat(query.resultList).isEmpty()
    }

    @Test
    fun deleteEmbedded() {
        // given
        val address1 = orderAddress { }

        entityManager.run {
            persist(address1)
            flush(); clear()
        }

        queryFactory.deleteQuery<OrderAddress> {
            where(
                and(
                    col(OrderAddress::id).equal(address1.id),
                    col(Address::zipCode).equal(address1.address.zipCode),
                )
            )
            associate(OrderAddress::class, Address::class, on(OrderAddress::address))
        }.executeUpdate()

        // when
        val query = queryFactory.selectQuery<OrderAddress> {
            select(entity(OrderAddress::class))
            from(entity(OrderAddress::class))
            where(
                and(
                    col(OrderAddress::id).equal(address1.id),
                    col(Address::zipCode).equal(address1.address.zipCode),
                )
            )
            associate(OrderAddress::class, Address::class, on(OrderAddress::address))
        }

        // then
        assertThat(query.resultList).isEmpty()

    }
}

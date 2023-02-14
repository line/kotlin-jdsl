package com.linecorp.kotlinjdsl.test.reactive.criteriaquery

import com.linecorp.kotlinjdsl.deleteQuery
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.entity.Address
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddress
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.reactive.CriteriaQueryDslIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import org.junit.jupiter.api.Test

abstract class AbstractCriteriaDeleteIntegrationTest<S> : CriteriaQueryDslIntegrationTest<S> {
    @Test
    fun delete() = runBlocking {
        // when
        val address1 = orderAddress { }

        persistAll(address1)

        withFactory { queryFactory ->
            queryFactory.deleteQuery<OrderAddress> {
                where(col(OrderAddress::id).equal(address1.id))
            }.executeUpdate()
        }

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<OrderAddress> {
                select(entity(OrderAddress::class))
                from(entity(OrderAddress::class))
                where(col(OrderAddress::id).equal(address1.id))
                associate(OrderAddress::class, Address::class, on(OrderAddress::address))
            }
        }

        // then
        assertThat(query).isEmpty()
    }

    @Test
    fun deleteInEmptyList() = runBlocking {
        // when
        val address1 = orderAddress { }

        persistAll(address1)

        withFactory { queryFactory ->
            queryFactory.deleteQuery<OrderAddress> {
                where(col(OrderAddress::id).`in`(emptyList<Long>()))
            }.executeUpdate()
        }

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<OrderAddress> {
                select(entity(OrderAddress::class))
                from(entity(OrderAddress::class))
                where(col(OrderAddress::id).equal(address1.id))
                associate(OrderAddress::class, Address::class, on(OrderAddress::address))
            }
        }

        // then
        assertThat(query).singleElement()
    }

    @Test
    fun deleteEmbedded() = runBlocking {
        // given
        val address1 = orderAddress { }

        persistAll(address1)

        withFactory { queryFactory ->
            queryFactory.deleteQuery<OrderAddress> {
                where(
                    and(
                        col(OrderAddress::id).equal(address1.id),
                        col(Address::zipCode).equal(address1.address.zipCode),
                    )
                )
                associate(OrderAddress::class, Address::class, on(OrderAddress::address))
            }.executeUpdate()
        }

        // when
        val query = withFactory { queryFactory ->
            queryFactory.listQuery<OrderAddress> {
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
        }

        // then
        assertThat(query).isEmpty()

    }

    @Test
    fun deleteByRefKey() = runBlocking {
        // given
        val order1 = order {
            groups = hashSetOf(
                orderGroup {
                    items = hashSetOf()
                    orderGroupName = "testOrderGroup"
                }
            )
        }

        val order2 = order {
            groups = hashSetOf(
                orderGroup {
                    items = hashSetOf()
                    orderGroupName = "orderGroup1"
                }
            )
        }
        val order3 = order {
            groups = hashSetOf(
                orderGroup {
                    items = hashSetOf()
                    orderGroupName = "orderGroup2"
                }
            )
        }

        persistAll(order1, order2, order3)

        // when
        withFactory { queryFactory ->
            queryFactory.deleteQuery<OrderGroup> {
                where(nestedCol(col(OrderGroup::order), Order::id).equal(order1.id))
            }.executeUpdate()
        }

        // then
        assertThat(withFactory { queryFactory ->
            queryFactory.listQuery<String> {
                select(col(OrderGroup::orderGroupName))
                from(entity(OrderGroup::class))
            }
        })
            .containsOnly("orderGroup1", "orderGroup2")
            .hasSize(2)

        assertThat(withFactory { queryFactory ->
            queryFactory.listQuery<String> {
                select(col(OrderGroup::orderGroupName))
                from(entity(OrderGroup::class))
                where(col(OrderGroup::orderGroupName).equal("testOrderGroup"))
            }
        }).isEmpty()
    }
}

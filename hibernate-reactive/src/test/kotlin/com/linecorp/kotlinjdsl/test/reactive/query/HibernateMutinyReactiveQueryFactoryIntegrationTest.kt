package com.linecorp.kotlinjdsl.test.reactive.query

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.*
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.singleQuery
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.EntityDsl
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.reactive.HibernateCriteriaIntegrationTest
import com.linecorp.kotlinjdsl.test.reactive.MutinySessionFactoryExtension
import com.linecorp.kotlinjdsl.test.reactive.retry
import com.linecorp.kotlinjdsl.updateQuery
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.runBlocking
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManagerFactory

@ExtendWith(MutinySessionFactoryExtension::class)
class HibernateMutinyReactiveQueryFactoryIntegrationTest : EntityDsl, WithKotlinJdslAssertions,
    HibernateCriteriaIntegrationTest {
    override lateinit var factory: Mutiny.SessionFactory
    override lateinit var entityManagerFactory: EntityManagerFactory

    @Test
    fun executeWithFactory() = runBlocking {
        val queryFactory = HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory, subqueryCreator = SubqueryCreatorImpl()
        )
        val order = order { purchaserId = 5000 }
        val sessionFactory = initFactory<Mutiny.SessionFactory>()
        retry(maxTries = 10, retryExceptions = listOf(NullPointerException::class, IllegalStateException::class)) {
            val actual = sessionFactory.withSession { session ->
                session.persist(order).flatMap { session.flush() }
                    .flatMap {
                        queryFactory.executeSessionWithFactory(session) { factory ->
                            factory.singleQuery<Order> {
                                select(entity(Order::class))
                                from(entity(Order::class))
                                where(col(Order::purchaserId).equal(5000))
                            }
                        }
                    }
            }.awaitSuspending()

            assertThat(actual.id).isEqualTo(order.id)
        }
        sessionFactory.close()
    }

    @Test
    fun listQuery(): Unit = runBlocking {
        val order = order {}
        persist(order)

        val queryFactory = HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        )

        val orders = queryFactory.listQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            fetch(Order::groups)
            fetch(OrderGroup::items)
            fetch(OrderGroup::address)
        }

        assertThat(orders)
            .containsExactly(order)
    }

    @Test
    fun singleQuery(): Unit = runBlocking {
        val order = order {}
        persist(order)

        val queryFactory = HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        )

        val actualOrder = queryFactory.singleQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            fetch(Order::groups)
            fetch(OrderGroup::items)
            fetch(OrderGroup::address)
            where(col(Order::id).equal(order.id))
        }

        assertThat(actualOrder)
            .isEqualTo(order)
    }

    @Test
    fun updateQuery(): Unit = runBlocking {
        val order = order {}
        persist(order)

        val queryFactory = HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        )

        queryFactory.updateQuery<Order> {
            where(col(Order::id).equal(order.id))
            set(col(Order::purchaserId), order.purchaserId + 1)
        }

        assertThat(queryFactory.singleQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            fetch(Order::groups)
            fetch(OrderGroup::items)
            fetch(OrderGroup::address)
            where(col(Order::id).equal(order.id))
        })
            .isEqualTo(order.copy(purchaserId = order.purchaserId + 1))
    }

    @Test
    fun deleteQuery(): Unit = runBlocking {
        val order = order {}
        persist(order)

        val queryFactory = HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        )

        queryFactory.deleteQuery<OrderItem> {
            where(col(OrderItem::id).equal(order.groups.first().items.first().id))
        }

        assertThat(queryFactory.listQuery<OrderItem> {
            select(entity(OrderItem::class))
            from(entity(OrderItem::class))
            where(col(OrderItem::id).equal(order.groups.first().items.first().id))
        }).isEmpty()
    }

    @Test
    fun subquery(): Unit = runBlocking {
        val orderItem1 = orderItem { }
        val orderItem2 = orderItem { }

        val order1 = order { purchaserId = 1000 }
        val order2 = order { purchaserId = 1000 }
        val order3 = order {
            purchaserId = 2000
            groups = hashSetOf(orderGroup { items = hashSetOf(orderItem1, orderItem2) })
        }
        persistAll(order1, order2, order3)

        val queryFactory = HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        )

        val subquery = queryFactory.subquery<Long> {
            val order = entity(Order::class, "o")

            select(count(col(order, Order::id)))
            from(order)
            where(col(order, Order::purchaserId).equal(col(Order::purchaserId)))
        }

        // then
        assertThat(queryFactory.listQuery<Long> {
            select(subquery)
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
        }).isEqualTo(listOf(2L, 2L, 1L))
    }

    @Test
    fun testTransaction(): Unit = runBlocking {
        val order = order {}
        persist(order)

        val producer = HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        )
        try {
            producer.transactionWithFactory { queryFactory ->
                queryFactory.listQuery<Order> {
                    select(entity(Order::class))
                    from(entity(Order::class))
                    fetch(Order::groups)
                    fetch(OrderGroup::items)
                    fetch(OrderGroup::address)
                }.thenCompose { orders ->
                    queryFactory.updateQuery<Order> {
                        where(col(Order::id).equal(orders.first().id))
                        set(col(Order::purchaserId), orders.first().purchaserId + 1)
                    }.executeUpdate.thenApply { orders }
                        .thenCompose {
                            queryFactory.updateQuery<Order> {
                                throw IllegalStateException("transaction rollback")
                            }.executeUpdate.thenApply { orders }
                        }
                }
            }
        } catch (e: IllegalStateException) {
            assertThat(e).hasMessage("transaction rollback")
        }

        assertThat(producer.transactionWithFactory { queryFactory ->
            queryFactory.singleQuery<Order> {
                select(entity(Order::class))
                from(entity(Order::class))
                fetch(Order::groups)
                fetch(OrderGroup::items)
                fetch(OrderGroup::address)
                where(col(Order::id).equal(order.id))
            }
        }).isEqualTo(order)
    }
}

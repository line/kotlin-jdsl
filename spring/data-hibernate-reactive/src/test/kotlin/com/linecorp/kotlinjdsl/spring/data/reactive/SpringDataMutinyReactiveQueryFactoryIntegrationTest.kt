package com.linecorp.kotlinjdsl.spring.data.reactive

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.querydsl.from.associate
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.*
import com.linecorp.kotlinjdsl.spring.reactive.listQuery
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataReactiveCriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataReactivePageableQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.singleQuery
import com.linecorp.kotlinjdsl.spring.reactive.updateQuery
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.EntityDsl
import com.linecorp.kotlinjdsl.test.entity.order.Order
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroup
import com.linecorp.kotlinjdsl.test.entity.order.OrderItem
import com.linecorp.kotlinjdsl.test.reactive.MutinySessionFactoryExtension
import com.linecorp.kotlinjdsl.test.reactive.query.initFactory
import com.linecorp.kotlinjdsl.test.reactive.runBlocking
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.future.await
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@ExtendWith(MutinySessionFactoryExtension::class)
internal class SpringDataMutinyReactiveQueryFactoryIntegrationTest : EntityDsl, WithKotlinJdslAssertions {
    private lateinit var sessionFactory: Mutiny.SessionFactory

    private lateinit var queryFactory: SpringDataHibernateMutinyReactiveQueryFactory

    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 1000 }
    private val order4 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() {
        queryFactory = SpringDataHibernateMutinyReactiveQueryFactory(
            sessionFactory = sessionFactory,
            subqueryCreator = SubqueryCreatorImpl()
        )
        sequenceOf(order1, order2, order3, order4).forEach {
            runBlocking {
                sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                    .awaitSuspending()
            }
        }
    }

    @Test
    fun executeWithFactory() = runBlocking {
        val order = order { purchaserId = 5000 }
        val sessionFactory = initFactory<Mutiny.SessionFactory>()
        val actual: Uni<Order> = sessionFactory.withSession { session ->
            queryFactory.executeSessionWithFactory(session) { factory ->
                session.persist(order).flatMap { session.flush() }.subscribeAsCompletionStage().thenCompose {
                    factory.singleQuery<Order> {
                        select(entity(Order::class))
                        from(entity(Order::class))
                        where(col(Order::purchaserId).equal(5000))
                    }
                }
            }
        }
        assertThat(actual.subscribeAsCompletionStage().await().id).isEqualTo(order.id)

        sessionFactory.close()
    }

    @Test
    fun singleQuery() = runBlocking {
        // when
        val actual = queryFactory.singleQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            where(col(Order::purchaserId).equal(2000))
        }

        // then
        assertThat(actual.id).isEqualTo(order4.id)
    }

    @Test
    fun listQuery() = runBlocking {
        // when
        val actual = queryFactory.listQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            fetch(Order::groups)
            fetch(OrderGroup::items)
            fetch(OrderGroup::address)
        }

        // then
        assertThat(actual).containsExactlyInAnyOrder(order1, order2, order3, order4)
    }

    @Test
    fun subquery() = runBlocking {
        val subquery = queryFactory.subquery<Long> {
            val order = entity(Order::class, "o")

            select(count(col(order, Order::id)))
            from(order)
            where(
                col(order, Order::purchaserId).equal(col(Order::purchaserId)),
            )
        }

        // then
        assertThat(queryFactory.listQuery<Long> {
            select(subquery)
            from(entity(Order::class))
            orderBy(col(Order::id).asc())
        }).isEqualTo(listOf(order3.id, order3.id, order3.id, order1.id))
    }


    @Test
    fun updateQuery() = runBlocking {
        // when
        queryFactory.updateQuery<Order> {
            where(col(Order::purchaserId).equal(2000))
            set(col(Order::purchaserId), 3000)
        }

        // then
        val actual = queryFactory.singleQuery<Order> {
            select(entity(Order::class))
            from(entity(Order::class))
            where(col(Order::purchaserId).equal(3000))
        }
        assertThat(actual.id).isEqualTo(order4.id)

        assertThat(actual.purchaserId).isEqualTo(3000)
    }

    @Test
    fun transaction() = runBlocking {
        // when
        try {
            queryFactory.transactionWithFactory { queryFactory ->
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

        assertThat(queryFactory.transactionWithFactory { queryFactory ->
            queryFactory.singleQuery<Order> {
                select(entity(Order::class))
                from(entity(Order::class))
                fetch(Order::groups)
                fetch(OrderGroup::items)
                fetch(OrderGroup::address)
                where(col(Order::id).equal(order1.id))
            }
        }).isEqualTo(order1)
    }

    @Test
    fun deleteQuery() = runBlocking {
        // when
        queryFactory.deleteQuery<OrderItem> {
            where(col(OrderItem::id).equal(order1.groups.first().items.first().id))
        }

        // then
        val actual = queryFactory.listQuery<OrderItem> {
            select(entity(OrderItem::class))
            from(entity(OrderItem::class))
            where(col(Order::id).equal(order1.id))
            associate(OrderItem::group)
            associate(OrderGroup::order)
        }
        assertThat(actual).isEmpty()
    }

    @Test
    fun pageQuery() = runBlocking {
        // when
        val actual = queryFactory.pageQuery<Long>(PageRequest.of(1, 2, Sort.by("id"))) {
            select(col(Order::id))
            from(entity(Order::class))
            where(col(Order::purchaserId).equal(1000))
        }

        // then
        assertThat(actual.content).isEqualTo(listOf(order3.id))

        assertThat(actual.totalElements).isEqualTo(3)
        assertThat(actual.totalPages).isEqualTo(2)
        assertThat(actual.number).isEqualTo(1)
    }

    @Test
    fun pageExtractWhereQuery() = runBlocking {
        // given
        val pageable = PageRequest.of(0, 10)
        fun WhereDsl.equalValueSpec() = column(Order::purchaserId).equal(1000L)

        val dsl: SpringDataReactivePageableQueryDsl<Order>.() -> Unit = {
            select(entity(Order::class))
            from(entity(Order::class))
            where(equalValueSpec())
        }

        val dslCriteria: SpringDataReactiveCriteriaQueryDsl<Order>.() -> Unit = {
            select(entity(Order::class))
            from(entity(Order::class))
            where(equalValueSpec())
        }
        // when
        val actual: Page<Order> = queryFactory.pageQuery(pageable, dsl)
        val actualList: List<Order> = queryFactory.listQuery(dslCriteria)

        // then
        assertThat(actual.content.size).isEqualTo(3)
        assertThat(actual.map { it.id }).containsExactlyInAnyOrder(order1.id, order2.id, order3.id)
        assertThat(actualList.map { it.id }).containsExactlyInAnyOrder(order1.id, order2.id, order3.id)
    }

    @Test
    fun `pageQuery with countProjection`() = runBlocking {
        // given
        val pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, Order::id.name))

        val dsl: SpringDataReactivePageableQueryDsl<Order>.() -> Unit = {
            select(entity(Order::class))
            from(entity(Order::class))
        }

        val countProjection: SpringDataReactivePageableQueryDsl<Long>.() -> SingleSelectClause<Long> = {
            select(count(column(Order::purchaserId)))
        }

        // when
        val actual: Page<Order> = queryFactory.pageQuery(pageable, dsl, countProjection)

        // then
        assertThat(actual).hasSize(1)
        assertThat(actual.content.first().id).isEqualTo(order4.id)
    }
}

package com.linecorp.kotlinjdsl.spring.data.reactive

import com.linecorp.kotlinjdsl.querydsl.ReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.GroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.HavingClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaReactiveQueryHintClauseImpl
import com.linecorp.kotlinjdsl.query.clause.hint.emptySqlHintClause
import com.linecorp.kotlinjdsl.query.clause.limit.ReactiveLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.OrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.CountSingleSelectClause
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.clause.set.SetClause
import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.creator.ReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.CountSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.EqualValueSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.reactive.query.clause.limit.SpringDataPageableLimitClause
import com.linecorp.kotlinjdsl.spring.reactive.query.clause.orderby.SpringDataPageableOrderByClause
import com.linecorp.kotlinjdsl.spring.reactive.SpringDataReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.concurrent.CompletableFuture

@ExtendWith(MockKExtension::class)
internal class SpringDataReactiveQueryFactoryImplTest : WithKotlinJdslAssertions {
    @InjectMockKs
    private lateinit var sut: SpringDataReactiveQueryFactoryImpl

    @MockK
    private lateinit var criteriaQueryCreator: ReactiveCriteriaQueryCreator

    @MockK
    private lateinit var subqueryCreator: SubqueryCreator

    @Test
    fun selectQuery() {
        // given
        val query: ReactiveQuery<Data1> = mockk()

        val data = ReactiveQueryDslImpl.CriteriaQuerySpecImpl<Data1, ReactiveQuery<Data1>>(
            select = SingleSelectClause(
                Data1::class.java,
                distinct = false,
                EntitySpec(Data1::class.java),
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
            orderBy = OrderByClause(emptyList()),
            limit = ReactiveLimitClause.empty(),
            sqlHint = emptySqlHintClause(),
            jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
        )

        every { criteriaQueryCreator.createQuery(any<ReactiveQueryDslImpl.CriteriaQuerySpecImpl<Data1, ReactiveQuery<Data1>>>()) } returns query

        // when
        val actual = sut.selectQuery(Data1::class.java) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // then
        assertThat(actual).isEqualTo(query)

        verify {
            criteriaQueryCreator.createQuery(data)
        }

        confirmVerified(criteriaQueryCreator)
    }

    @Test
    fun updateQuery() {
        // given
        val query: ReactiveQuery<Data1> = mockk()

        every { criteriaQueryCreator.createQuery(any<ReactiveQueryDslImpl.CriteriaUpdateQuerySpecImpl<Data1, ReactiveQuery<Data1>>>()) } returns query

        // when
        val actual = sut.updateQuery(Data1::class) {
            where(col(Data1::id).equal(1))
            set(col(Data1::id), 2)
        }

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            val columnSpec = ColumnSpec<Int>(EntitySpec(Data1::class.java), Data1::id.name)
            criteriaQueryCreator.createQuery(
                ReactiveQueryDslImpl.CriteriaUpdateQuerySpecImpl(
                    from = FromClause(EntitySpec(Data1::class.java)),
                    associate = SimpleAssociatedJoinClause(emptyList()),
                    where = WhereClause(EqualValueSpec(columnSpec, 1)),
                    jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
                    sqlHint = emptySqlHintClause(),
                    set = SetClause(mapOf(ColumnSpec<Any>(EntitySpec(Data1::class.java), Data1::id.name) to 2)),
                    targetEntity = Data1::class.java
                )
            )
        }

        confirmVerified(criteriaQueryCreator)
    }

    @Test
    fun deleteQuery() {
        // given
        val query: ReactiveQuery<Data1> = mockk()

        every { criteriaQueryCreator.createQuery(any<ReactiveQueryDslImpl.CriteriaDeleteQuerySpecImpl<Data1, ReactiveQuery<Data1>>>()) } returns query

        // when
        val actual = sut.deleteQuery(Data1::class) {
            where(col(Data1::id).equal(1))
        }

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            val columnSpec = ColumnSpec<Int>(EntitySpec(Data1::class.java), Data1::id.name)
            criteriaQueryCreator.createQuery(
                ReactiveQueryDslImpl.CriteriaDeleteQuerySpecImpl(
                    from = FromClause(EntitySpec(Data1::class.java)),
                    associate = SimpleAssociatedJoinClause(emptyList()),
                    where = WhereClause(EqualValueSpec(columnSpec, 1)),
                    jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
                    sqlHint = emptySqlHintClause(),
                    targetEntity = Data1::class.java
                )
            )
        }

        confirmVerified(criteriaQueryCreator)
    }

    @Test
    fun subquery() {
        // when
        val actual = sut.subquery(Data1::class.java) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // then
        val subquerySpec = ReactiveQueryDslImpl.SubquerySpecImpl(
            select = SingleSelectClause(
                Data1::class.java,
                distinct = false,
                EntitySpec(Data1::class.java)
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
        )

        assertThat(actual).isEqualTo(SubqueryExpressionSpec(subquerySpec, subqueryCreator))
    }

    @Test
    fun pageQuery() = runBlocking {
        // given
        val pageable = PageRequest.of(0, 5)

        val expectedPageableSpec: CriteriaQuerySpec<Data1, ReactiveQuery<Data1>> = ReactiveQueryDslImpl.CriteriaQuerySpecImpl(
            select = SingleSelectClause(
                Data1::class.java,
                distinct = false,
                EntitySpec(Data1::class.java)
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
            orderBy = SpringDataPageableOrderByClause(pageable),
            limit = SpringDataPageableLimitClause(pageable),
            jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
            sqlHint = emptySqlHintClause(),
        )

        val expectedPageableCountSpec = ReactiveQueryDslImpl.CriteriaQuerySpecImpl(
            select = CountSingleSelectClause(
                distinct = false,
                EntitySpec(Data1::class.java)
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
            orderBy = OrderByClause(emptyList()),
            limit = ReactiveLimitClause.empty<ReactiveQuery<Long>>(),
            jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
            sqlHint = emptySqlHintClause(),
        )

        val pageableQuery: ReactiveQuery<Data1> = mockk {
            every { resultList } returns CompletableFuture.completedFuture(listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5)))
        }

        val pageableCountQuery: ReactiveQuery<Long> = mockk {
            every { resultList } returns CompletableFuture.completedFuture(listOf(50L))
        }

        coEvery { criteriaQueryCreator.createQuery(expectedPageableSpec) } returns pageableQuery
        coEvery { criteriaQueryCreator.createQuery(expectedPageableCountSpec) } returns pageableCountQuery

        // when
        val actual = sut.pageQuery(Data1::class.java, pageable) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }.await()

        // then
        assertThat(actual).isEqualTo(PageImpl(listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5)), pageable, 50))

        coVerify(exactly = 1) {
            criteriaQueryCreator.createQuery(expectedPageableSpec)
            criteriaQueryCreator.createQuery(expectedPageableCountSpec)

            pageableQuery.resultList
            pageableCountQuery.resultList
        }

        confirmVerified(pageableQuery, pageableCountQuery, criteriaQueryCreator)
    }

    @Test
    fun `pageQuery with countProjection`() = runBlocking {
        // given
        val pageable = PageRequest.of(0, 5)

        val expectedPageableSpec: CriteriaQuerySpec<Data1, ReactiveQuery<Data1>> = ReactiveQueryDslImpl.CriteriaQuerySpecImpl(
            select = SingleSelectClause(
                Data1::class.java,
                distinct = false,
                EntitySpec(Data1::class.java)
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
            orderBy = SpringDataPageableOrderByClause(pageable),
            limit = SpringDataPageableLimitClause(pageable),
            jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
            sqlHint = emptySqlHintClause(),
        )

        val expectedPageableCountSpec: CriteriaQuerySpec<Long, ReactiveQuery<Long>> = ReactiveQueryDslImpl.CriteriaQuerySpecImpl(
            select = SingleSelectClause(
                returnType = Long::class.java,
                distinct = false,
                expression = CountSpec(
                    distinct = false,
                    expression = ColumnSpec<Long>(EntitySpec(Data1::class.java), "id")
                )
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
            orderBy = OrderByClause(emptyList()),
            limit = ReactiveLimitClause.empty(),
            jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
            sqlHint = emptySqlHintClause(),
        )

        val pageableQuery: ReactiveQuery<Data1> = mockk {
            every { resultList } returns CompletableFuture.completedFuture(listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5)))
        }

        val pageableCountQuery: ReactiveQuery<Long> = mockk {
            every { resultList } returns CompletableFuture.completedFuture(listOf(50L))
        }

        every { criteriaQueryCreator.createQuery(expectedPageableSpec) } returns pageableQuery
        every { criteriaQueryCreator.createQuery(expectedPageableCountSpec) } returns pageableCountQuery

        // when
        val actual = sut.pageQuery(Data1::class.java, pageable, dsl = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }, countProjection = {
            select(count(column(Data1::id)))
        }).await()

        // then
        assertThat(actual).isEqualTo(PageImpl(listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5)), pageable, 50))

        coVerify(exactly = 1) {
            criteriaQueryCreator.createQuery(expectedPageableSpec)
            criteriaQueryCreator.createQuery(expectedPageableCountSpec)

            pageableQuery.resultList
            pageableCountQuery.resultList
        }

        confirmVerified(pageableQuery, pageableCountQuery, criteriaQueryCreator)
    }

    data class Data1(
        val id: Int = 1
    )
}

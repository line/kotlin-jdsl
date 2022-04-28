package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.*
import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.CriteriaQueryGroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.CriteriaQueryHavingClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.CriteriaQueryOrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.CriteriaQuerySelectClause
import com.linecorp.kotlinjdsl.query.clause.set.SetClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause
import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.criteria.*

@ExtendWith(MockKExtension::class)
internal class MutinyStatelessReactiveCriteriaQueryCreatorTest : WithKotlinJdslAssertions {
    @InjectMockKs
    private lateinit var sut: MutinyStatelessReactiveCriteriaQueryCreator

    @MockK
    private lateinit var session: Mutiny.StatelessSession

    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun createQuery() {
        data class TestCriteriaQuerySpec<T>(
            override val select: CriteriaQuerySelectClause<T>,
            override val from: FromClause<*>,
            override val join: JoinClause,
            override val where: CriteriaQueryWhereClause,
            override val groupBy: CriteriaQueryGroupByClause,
            override val having: CriteriaQueryHavingClause,
            override val orderBy: CriteriaQueryOrderByClause,
            override val limit: QueryLimitClause<ReactiveQuery<T>>,
            override val jpaHint: JpaQueryHintClause<ReactiveQuery<T>>,
            override val sqlHint: SqlQueryHintClause<ReactiveQuery<T>>,
        ) : CriteriaQuerySpec<T, ReactiveQuery<T>>
        // given
        val createdQuery: CriteriaQuery<Int> = mockk()
        val query = slot<HibernateMutinyReactiveQuery<Int>>()
        val stageQuery: Mutiny.Query<Int> = mockk()

        val select: CriteriaQuerySelectClause<Int> = mockk {
            every { returnType } returns Int::class.java
        }
        val from: FromClause<*> = mockk()
        val join: JoinClause = mockk()
        val where: CriteriaQueryWhereClause = mockk()
        val groupBy: CriteriaQueryGroupByClause = mockk()
        val having: CriteriaQueryHavingClause = mockk()
        val orderBy: CriteriaQueryOrderByClause = mockk()
        val limit: QueryLimitClause<ReactiveQuery<Int>> = mockk()
        val jpaHint: JpaQueryHintClause<ReactiveQuery<Int>> = mockk()
        val sqlHint: SqlQueryHintClause<ReactiveQuery<Int>> = mockk()

        val spec: CriteriaQuerySpec<Int, ReactiveQuery<Int>> = TestCriteriaQuerySpec(
            from = from,
            join = join,
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
            limit = limit,
            jpaHint = jpaHint,
            sqlHint = sqlHint,
            select = select
        )

        every { session.createQuery(createdQuery) } returns stageQuery
        every { criteriaBuilder.createQuery(Int::class.java) } returns createdQuery
        every { from.join(join, createdQuery, criteriaBuilder) } returns froms
        every { select.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { where.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { groupBy.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { having.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { orderBy.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { limit.apply(capture(query)) } just runs
        every { jpaHint.apply(capture(query)) } just runs
        every { sqlHint.apply(capture(query)) } just runs

        // when
        val actual = sut.createQuery(spec)

        // then
        assertThat(actual).isEqualTo(query.captured)

        verify(exactly = 1) {
            session.createQuery(createdQuery)
            criteriaBuilder.createQuery(Int::class.java)
            from.join(join, createdQuery, criteriaBuilder)
            select.returnType
            select.apply(froms, createdQuery, criteriaBuilder)
            where.apply(froms, createdQuery, criteriaBuilder)
            groupBy.apply(froms, createdQuery, criteriaBuilder)
            having.apply(froms, createdQuery, criteriaBuilder)
            orderBy.apply(froms, createdQuery, criteriaBuilder)
            limit.apply(query.captured)
            jpaHint.apply(query.captured)
            sqlHint.apply(query.captured)
        }

        confirmVerified(
            select, from, join, where, groupBy, having, orderBy, limit, jpaHint, sqlHint,
            createdQuery,
            session, froms, criteriaBuilder
        )
    }

    @Test
    fun createUpdateQuery() {
        data class TestCriteriaUpdateQuerySpec<T>(
            override val targetEntity: Class<T>,
            override val from: FromClause<T>,
            override val associate: SimpleAssociatedJoinClause,
            override val where: CriteriaQueryWhereClause,
            override val jpaHint: JpaQueryHintClause<ReactiveQuery<T>>,
            override val sqlHint: SqlQueryHintClause<ReactiveQuery<T>>,
            override val set: SetClause
        ) : CriteriaUpdateQuerySpec<T, ReactiveQuery<T>>
        // given
        val createdQuery: CriteriaUpdate<Int> = mockk()
        val query = slot<HibernateMutinyReactiveQuery<Int>>()
        val stageQuery: Mutiny.Query<Int> = mockk()

        val from: FromClause<Int> = mockk()
        val associate = SimpleAssociatedJoinClause(emptyList())
        val where: CriteriaQueryWhereClause = mockk()
        val jpaHint: JpaQueryHintClause<ReactiveQuery<Int>> = mockk()
        val sqlHint: SqlQueryHintClause<ReactiveQuery<Int>> = mockk()
        val set: SetClause = mockk()

        val spec: CriteriaUpdateQuerySpec<Int, ReactiveQuery<Int>> = TestCriteriaUpdateQuerySpec(
            from = from,
            associate = associate,
            where = where,
            jpaHint = jpaHint,
            sqlHint = sqlHint,
            set = set,
            targetEntity = Int::class.java
        )

        val setKey = mockk<Path<Int>>()
        every { createdQuery.set(setKey, 1234) } returns createdQuery

        every { session.createQuery(createdQuery) } returns stageQuery
        every { criteriaBuilder.createCriteriaUpdate(Int::class.java) } returns createdQuery
        every { from.associate(associate, createdQuery) } returns froms
        every { where.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { jpaHint.apply(capture(query)) } just runs
        every { sqlHint.apply(capture(query)) } just runs
        every { set.apply(froms, createdQuery, criteriaBuilder) } just runs

        // when
        val actual = sut.createQuery(spec)

        // then
        assertThat(actual).isEqualTo(query.captured)

        verify(exactly = 1) {
            session.createQuery(createdQuery)
            criteriaBuilder.createCriteriaUpdate(Int::class.java)
            from.associate(associate, createdQuery)
            where.apply(froms, createdQuery, criteriaBuilder)
            set.apply(froms, createdQuery, criteriaBuilder)
            jpaHint.apply(query.captured)
            sqlHint.apply(query.captured)
        }

        confirmVerified(
            from, where, jpaHint, sqlHint, set,
            createdQuery,
            session, froms, criteriaBuilder
        )
    }

    @Test
    fun createDeleteQuery() {
        data class TestCriteriaDeleteQuerySpec<T>(
            override val targetEntity: Class<T>,
            override val from: FromClause<T>,
            override val associate: SimpleAssociatedJoinClause,
            override val where: CriteriaQueryWhereClause,
            override val jpaHint: JpaQueryHintClause<ReactiveQuery<T>>,
            override val sqlHint: SqlQueryHintClause<ReactiveQuery<T>>,
        ) : CriteriaDeleteQuerySpec<T, ReactiveQuery<T>>
        // given
        val createdQuery: CriteriaDelete<Int> = mockk()
        val query = slot<HibernateMutinyReactiveQuery<Int>>()
        val stageQuery: Mutiny.Query<Int> = mockk()

        val from: FromClause<Int> = mockk()
        val associate = SimpleAssociatedJoinClause(emptyList())
        val where: CriteriaQueryWhereClause = mockk()
        val jpaHint: JpaQueryHintClause<ReactiveQuery<Int>> = mockk()
        val sqlHint: SqlQueryHintClause<ReactiveQuery<Int>> = mockk()
        val set: SetClause = mockk()

        val spec: CriteriaDeleteQuerySpec<Int, ReactiveQuery<Int>> = TestCriteriaDeleteQuerySpec(
            from = from,
            associate = associate,
            where = where,
            jpaHint = jpaHint,
            sqlHint = sqlHint,
            targetEntity = Int::class.java
        )

        every { session.createQuery(createdQuery) } returns stageQuery
        every { criteriaBuilder.createCriteriaDelete(Int::class.java) } returns createdQuery
        every { from.associate(associate, createdQuery) } returns froms
        every { where.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { jpaHint.apply(capture(query)) } just runs
        every { sqlHint.apply(capture(query)) } just runs

        // when
        val actual = sut.createQuery(spec)

        // then
        assertThat(actual).isEqualTo(query.captured)

        verify(exactly = 1) {
            session.createQuery(createdQuery)
            criteriaBuilder.createCriteriaDelete(Int::class.java)
            from.associate(associate, createdQuery)
            where.apply(froms, createdQuery, criteriaBuilder)
            jpaHint.apply(query.captured)
            sqlHint.apply(query.captured)
        }

        confirmVerified(
            from, where, jpaHint, sqlHint, set,
            createdQuery,
            session, froms, criteriaBuilder
        )
    }
}

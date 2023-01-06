package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaDeleteQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.*

@Suppress("UnusedEquals")
@ExtendWith(MockKExtension::class)
internal class CriteriaQueryCreatorImplTest : WithKotlinJdslAssertions {
    @InjectMockKs
    private lateinit var sut: CriteriaQueryCreatorImpl

    @MockK
    private lateinit var em: EntityManager

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
            override val limit: QueryLimitClause<TypedQuery<T>>,
            override val jpaHint: JpaQueryHintClause<TypedQuery<T>>,
            override val sqlHint: SqlQueryHintClause<TypedQuery<T>>,
        ) : CriteriaQuerySpec<T, TypedQuery<T>>
        // given
        val createdQuery: CriteriaQuery<Int> = mockk()
        val typedQuery: TypedQuery<Int> = mockk()

        val select: CriteriaQuerySelectClause<Int> = mockk {
            every { returnType } returns Int::class.java
        }
        val from: FromClause<*> = mockk()
        val join: JoinClause = mockk()
        val where: CriteriaQueryWhereClause = mockk()
        val groupBy: CriteriaQueryGroupByClause = mockk()
        val having: CriteriaQueryHavingClause = mockk()
        val orderBy: CriteriaQueryOrderByClause = mockk()
        val limit: QueryLimitClause<TypedQuery<Int>> = mockk()
        val jpaHint: JpaQueryHintClause<TypedQuery<Int>> = mockk()
        val sqlHint: SqlQueryHintClause<TypedQuery<Int>> = mockk()

        val spec: CriteriaQuerySpec<Int, TypedQuery<Int>> = TestCriteriaQuerySpec(
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

        every { em.criteriaBuilder } returns criteriaBuilder
        every { em.createQuery(createdQuery) } returns typedQuery
        every { criteriaBuilder.createQuery(Int::class.java) } returns createdQuery
        every { from.join(join, createdQuery, criteriaBuilder) } returns froms
        every { select.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { where.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { groupBy.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { having.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { orderBy.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { limit.apply(typedQuery) } just runs
        every { jpaHint.apply(typedQuery) } just runs
        every { sqlHint.apply(typedQuery) } just runs

        // when
        val actual = sut.createQuery(spec)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verify(exactly = 1) {
            em.criteriaBuilder
            em.createQuery(createdQuery)
            criteriaBuilder.createQuery(Int::class.java)
            from.join(join, createdQuery, criteriaBuilder)
            select.returnType
            select.apply(froms, createdQuery, criteriaBuilder)
            where.apply(froms, createdQuery, criteriaBuilder)
            groupBy.apply(froms, createdQuery, criteriaBuilder)
            having.apply(froms, createdQuery, criteriaBuilder)
            orderBy.apply(froms, createdQuery, criteriaBuilder)
            limit.apply(typedQuery)
            jpaHint.apply(typedQuery)
            sqlHint.apply(typedQuery)
            typedQuery == typedQuery
        }

        confirmVerified(
            select, from, join, where, groupBy, having, orderBy, limit, jpaHint, sqlHint,
            createdQuery, typedQuery,
            em, froms, criteriaBuilder
        )
    }

    @Test
    fun createUpdateQuery() {
        data class TestCriteriaUpdateQuerySpec<T>(
            override val targetEntity: Class<T>,
            override val from: FromClause<T>,
            override val associate: SimpleAssociatedJoinClause,
            override val where: CriteriaQueryWhereClause,
            override val jpaHint: JpaQueryHintClause<Query>,
            override val sqlHint: SqlQueryHintClause<Query>,
            override val set: SetClause
        ) : CriteriaUpdateQuerySpec<T, Query>
        // given
        val createdQuery: CriteriaUpdate<Int> = mockk()
        val query: Query = mockk()

        val from: FromClause<Int> = mockk()
        val associate = SimpleAssociatedJoinClause(emptyList())
        val where: CriteriaQueryWhereClause = mockk()
        val jpaHint: JpaQueryHintClause<Query> = mockk()
        val sqlHint: SqlQueryHintClause<Query> = mockk()
        val set: SetClause = mockk()

        val spec: CriteriaUpdateQuerySpec<Int, Query> = TestCriteriaUpdateQuerySpec(
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

        every { em.criteriaBuilder } returns criteriaBuilder
        every { em.createQuery(createdQuery) } returns query
        every { criteriaBuilder.createCriteriaUpdate(Int::class.java) } returns createdQuery
        every { from.associate(associate, createdQuery) } returns froms
        every { where.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { jpaHint.apply(query) } just runs
        every { sqlHint.apply(query) } just runs
        every { set.apply(froms, createdQuery, criteriaBuilder) } just runs

        // when
        val actual = sut.createQuery(spec)

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            em.criteriaBuilder
            em.createQuery(createdQuery)
            criteriaBuilder.createCriteriaUpdate(Int::class.java)
            from.associate(associate, createdQuery)
            where.apply(froms, createdQuery, criteriaBuilder)
            set.apply(froms, createdQuery, criteriaBuilder)
            jpaHint.apply(query)
            sqlHint.apply(query)
            query == query
        }

        confirmVerified(
            from, where, jpaHint, sqlHint, set,
            createdQuery, query,
            em, froms, criteriaBuilder
        )
    }

    @Test
    fun createDeleteQuery() {
        data class TestCriteriaDeleteQuerySpec<T>(
            override val targetEntity: Class<T>,
            override val from: FromClause<T>,
            override val associate: SimpleAssociatedJoinClause,
            override val where: CriteriaQueryWhereClause,
            override val jpaHint: JpaQueryHintClause<Query>,
            override val sqlHint: SqlQueryHintClause<Query>,
        ) : CriteriaDeleteQuerySpec<T, Query>
        // given
        val createdQuery: CriteriaDelete<Int> = mockk()
        val query: Query = mockk()

        val from: FromClause<Int> = mockk()
        val associate = SimpleAssociatedJoinClause(emptyList())
        val where: CriteriaQueryWhereClause = mockk()
        val jpaHint: JpaQueryHintClause<Query> = mockk()
        val sqlHint: SqlQueryHintClause<Query> = mockk()
        val set: SetClause = mockk()

        val spec: CriteriaDeleteQuerySpec<Int, Query> = TestCriteriaDeleteQuerySpec(
            from = from,
            associate = associate,
            where = where,
            jpaHint = jpaHint,
            sqlHint = sqlHint,
            targetEntity = Int::class.java
        )

        every { em.criteriaBuilder } returns criteriaBuilder
        every { em.createQuery(createdQuery) } returns query
        every { criteriaBuilder.createCriteriaDelete(Int::class.java) } returns createdQuery
        every { from.associate(associate, createdQuery) } returns froms
        every { where.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { jpaHint.apply(query) } just runs
        every { sqlHint.apply(query) } just runs

        // when
        val actual = sut.createQuery(spec)

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            em.criteriaBuilder
            em.createQuery(createdQuery)
            criteriaBuilder.createCriteriaDelete(Int::class.java)
            from.associate(associate, createdQuery)
            where.apply(froms, createdQuery, criteriaBuilder)
            jpaHint.apply(query)
            sqlHint.apply(query)
            query == query
        }

        confirmVerified(
            from, where, jpaHint, sqlHint, set,
            createdQuery, query,
            em, froms, criteriaBuilder
        )
    }
}

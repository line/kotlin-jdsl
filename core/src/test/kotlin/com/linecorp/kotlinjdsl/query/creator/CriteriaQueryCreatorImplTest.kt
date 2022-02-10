package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.CriteriaQueryGroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.CriteriaQueryHavingClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.SqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.limit.QueryLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.CriteriaQueryOrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.CriteriaQuerySelectClause
import com.linecorp.kotlinjdsl.query.clause.where.CriteriaQueryWhereClause
import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import javax.persistence.criteria.*

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
        // given
        val createdQuery: CriteriaQuery<Int> = mockk()
        val typedQuery: TypedQuery<Int> = mockk()

        val select: CriteriaQuerySelectClause<Int> = mockk {
            every { returnType } returns Int::class.java
        }
        val from: FromClause = mockk()
        val join: JoinClause = mockk()
        val where: CriteriaQueryWhereClause = mockk()
        val groupBy: CriteriaQueryGroupByClause = mockk()
        val having: CriteriaQueryHavingClause = mockk()
        val orderBy: CriteriaQueryOrderByClause = mockk()
        val limit: QueryLimitClause = mockk()
        val jpaHint: JpaQueryHintClause = mockk()
        val sqlHint: SqlQueryHintClause = mockk()

        val spec: CriteriaQuerySpec<Int> = mockk {
            every { this@mockk.select } returns select
            every { this@mockk.from } returns from
            every { this@mockk.join } returns join
            every { this@mockk.where } returns where
            every { this@mockk.groupBy } returns groupBy
            every { this@mockk.having } returns having
            every { this@mockk.orderBy } returns orderBy
            every { this@mockk.limit } returns limit
            every { this@mockk.jpaHint } returns jpaHint
            every { this@mockk.sqlHint } returns sqlHint
        }

        every { em.criteriaBuilder } returns criteriaBuilder
        every { em.createQuery(createdQuery) } returns typedQuery
        every { criteriaBuilder.createQuery(Int::class.java) } returns createdQuery
        every { from.join(join, createdQuery) } returns froms
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

        verify(exactly = 2) {
            spec.select
        }

        verify(exactly = 1) {
            spec.from
            spec.join
            spec.where
            spec.groupBy
            spec.having
            spec.orderBy
            spec.limit
            spec.jpaHint
            spec.sqlHint

            em.criteriaBuilder
            em.createQuery(createdQuery)
            criteriaBuilder.createQuery(Int::class.java)
            from.join(join, createdQuery)
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
            spec, select, from, join, where, groupBy, having, orderBy, limit, jpaHint, sqlHint,
            createdQuery, typedQuery,
            em, froms, criteriaBuilder
        )
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun createUpdateQuery() {
        // given
        val createdQuery: CriteriaUpdate<Int> = mockk()
        val query: Query = mockk()

        val from: FromClause = mockk()
        val join: JoinClause = mockk()
        val where: CriteriaQueryWhereClause = mockk()
        val jpaHint: JpaQueryHintClause = mockk()
        val sqlHint: SqlQueryHintClause = mockk()
        val column = mockk<ColumnSpec<*>>()
        val params: Map<ColumnSpec<*>, Any?> = mapOf(column to 1234)

        val spec: CriteriaUpdateQuerySpec<Int> = mockk {
            every { this@mockk.from } returns from
            every { this@mockk.join } returns join
            every { this@mockk.where } returns where
            every { this@mockk.jpaHint } returns jpaHint
            every { this@mockk.sqlHint } returns sqlHint
            every { this@mockk.params } returns params
            every { targetEntity } returns Int::class.java
        }

        val setKey = mockk<Path<Int>>()
        every { column.toCriteriaExpression(froms, createdQuery, criteriaBuilder) } returns setKey
        every { createdQuery.set(setKey, 1234) } returns createdQuery

        every { em.criteriaBuilder } returns criteriaBuilder
        every { em.createQuery(createdQuery) } returns query
        every { criteriaBuilder.createCriteriaUpdate(Int::class.java) } returns createdQuery
        every { from.associate(join, createdQuery as CriteriaUpdate<in Any>, Int::class.java) } returns froms
        every { where.apply(froms, createdQuery, criteriaBuilder) } just runs
        every { jpaHint.apply(query) } just runs
        every { sqlHint.apply(query) } just runs

        // when
        val actual = sut.createQuery(spec)

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 2) {
            spec.targetEntity
        }

        verify(exactly = 1) {
            spec.from
            spec.join
            spec.where
            spec.jpaHint
            spec.sqlHint
            spec.params
            column.toCriteriaExpression(froms, createdQuery, criteriaBuilder)

            em.criteriaBuilder
            em.createQuery(createdQuery)
            criteriaBuilder.createCriteriaUpdate(Int::class.java)
            from.associate(join, createdQuery as CriteriaUpdate<in Any>, Int::class.java)
            where.apply(froms, createdQuery, criteriaBuilder)
            jpaHint.apply(query)
            sqlHint.apply(query)
            createdQuery.set(setKey, 1234)
            query == query
        }

        confirmVerified(
            spec, from, where, jpaHint, sqlHint, column,
            createdQuery, query,
            em, froms, criteriaBuilder
        )
    }
}

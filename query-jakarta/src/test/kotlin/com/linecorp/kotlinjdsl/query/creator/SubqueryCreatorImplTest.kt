package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.SubqueryGroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.SubqueryHavingClause
import com.linecorp.kotlinjdsl.query.clause.select.SubquerySelectClause
import com.linecorp.kotlinjdsl.query.clause.where.SubqueryWhereClause
import com.linecorp.kotlinjdsl.query.spec.Froms
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.criteria.*

@ExtendWith(MockKExtension::class)
internal class SubqueryCreatorImplTest : WithAssertions {
    private val sut: SubqueryCreatorImpl = SubqueryCreatorImpl()

    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @MockK
    private lateinit var criteriaQuery: CriteriaQuery<Int>

    @MockK
    private lateinit var criteriaUpdate: CriteriaUpdate<Int>

    @MockK
    private lateinit var criteriaDelete: CriteriaDelete<Int>

    @MockK
    private lateinit var subquery: Subquery<Int>

    data class TestSubquerySpec<T : Any>(
        override val select: SubquerySelectClause<T>,
        override val from: FromClause<T>,
        override val join: JoinClause,
        override val where: SubqueryWhereClause,
        override val groupBy: SubqueryGroupByClause,
        override val having: SubqueryHavingClause
    ) : SubquerySpec<T>

    @Test
    fun createQuery() {
        // given
        val createdQuery: Subquery<Int> = mockk()

        val createdFroms: Froms = mockk()
        val mergedFroms: Froms = mockk()

        val select: SubquerySelectClause<Int> = mockk {
            every { returnType } returns Int::class.java
        }
        val from: FromClause<Int> = mockk()
        val join: JoinClause = mockk()
        val where: SubqueryWhereClause = mockk()
        val groupBy: SubqueryGroupByClause = mockk()
        val having: SubqueryHavingClause = mockk()

        val spec: SubquerySpec<Int> = TestSubquerySpec(
            select = select,
            from = from,
            join = join,
            where = where,
            groupBy = groupBy,
            having = having
        )

        every { criteriaQuery.subquery(Int::class.java) } returns createdQuery
        every { from.join(join, createdQuery, criteriaBuilder) } returns createdFroms
        every { createdFroms + froms } returns mergedFroms
        every { select.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { where.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { groupBy.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { having.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs

        // when
        val actual = sut.createQuery(spec, froms, criteriaQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(createdQuery)

        verify(exactly = 1) {
            criteriaQuery.subquery(Int::class.java)
            from.join(join, createdQuery, criteriaBuilder)
            createdFroms + froms
            select.returnType
            select.apply(mergedFroms, createdQuery, criteriaBuilder)
            where.apply(mergedFroms, createdQuery, criteriaBuilder)
            groupBy.apply(mergedFroms, createdQuery, criteriaBuilder)
            having.apply(mergedFroms, createdQuery, criteriaBuilder)
            createdQuery == createdQuery
        }

        confirmVerified(
            select, from, join, where, groupBy, having,
            createdFroms, mergedFroms, createdQuery,
            criteriaQuery, subquery, criteriaBuilder
        )
    }

    @Test
    fun createUpdateQuery() {
        // given
        val createdQuery: Subquery<Int> = mockk()

        val createdFroms: Froms = mockk()
        val mergedFroms: Froms = mockk()

        val select: SubquerySelectClause<Int> = mockk {
            every { returnType } returns Int::class.java
        }
        val from: FromClause<Int> = mockk()
        val join: JoinClause = mockk()
        val where: SubqueryWhereClause = mockk()
        val groupBy: SubqueryGroupByClause = mockk()
        val having: SubqueryHavingClause = mockk()

        val spec: SubquerySpec<Int> = TestSubquerySpec(
            select = select,
            from = from,
            join = join,
            where = where,
            groupBy = groupBy,
            having = having
        )

        every { criteriaUpdate.subquery(Int::class.java) } returns createdQuery
        every { from.join(join, createdQuery, criteriaBuilder) } returns createdFroms
        every { createdFroms + froms } returns mergedFroms
        every { select.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { where.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { groupBy.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { having.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs

        // when
        val actual = sut.createQuery(spec, froms, criteriaUpdate, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(createdQuery)

        verify(exactly = 1) {
            criteriaUpdate.subquery(Int::class.java)
            from.join(join, createdQuery, criteriaBuilder)
            createdFroms + froms
            select.returnType
            select.apply(mergedFroms, createdQuery, criteriaBuilder)
            where.apply(mergedFroms, createdQuery, criteriaBuilder)
            groupBy.apply(mergedFroms, createdQuery, criteriaBuilder)
            having.apply(mergedFroms, createdQuery, criteriaBuilder)
            createdQuery == createdQuery
        }

        confirmVerified(
            select, from, join, where, groupBy, having,
            createdFroms, mergedFroms, createdQuery,
            criteriaUpdate, subquery, criteriaBuilder
        )
    }
}

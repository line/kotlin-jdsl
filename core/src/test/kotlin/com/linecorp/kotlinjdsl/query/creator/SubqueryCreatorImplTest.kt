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
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Subquery

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
    private lateinit var subquery: Subquery<Int>

    @Test
    fun createQuery() {
        // given
        val createdQuery: Subquery<Int> = mockk()

        val createdFroms: Froms = mockk()
        val mergedFroms: Froms = mockk()

        val select: SubquerySelectClause<Int> = mockk {
            every { returnType } returns Int::class.java
        }
        val from: FromClause = mockk()
        val join: JoinClause = mockk()
        val where: SubqueryWhereClause = mockk()
        val groupBy: SubqueryGroupByClause = mockk()
        val having: SubqueryHavingClause = mockk()

        val spec: SubquerySpec<Int> = mockk {
            every { this@mockk.select } returns select
            every { this@mockk.from } returns from
            every { this@mockk.join } returns join
            every { this@mockk.where } returns where
            every { this@mockk.groupBy } returns groupBy
            every { this@mockk.having } returns having
        }

        every { criteriaQuery.subquery(Int::class.java) } returns createdQuery
        every { from.join(join, createdQuery) } returns createdFroms
        every { createdFroms + froms } returns mergedFroms
        every { select.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { where.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { groupBy.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs
        every { having.apply(mergedFroms, createdQuery, criteriaBuilder) } just runs

        // when
        val actual = sut.createQuery(spec, froms, criteriaQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(createdQuery)

        verify(exactly = 2) {
            spec.select
        }

        verify(exactly = 1) {
            spec.from
            spec.join
            spec.where
            spec.groupBy
            spec.having

            criteriaQuery.subquery(Int::class.java)
            from.join(join, createdQuery)
            createdFroms + froms
            select.returnType
            select.apply(mergedFroms, createdQuery, criteriaBuilder)
            where.apply(mergedFroms, createdQuery, criteriaBuilder)
            groupBy.apply(mergedFroms, createdQuery, criteriaBuilder)
            having.apply(mergedFroms, createdQuery, criteriaBuilder)
            createdQuery == createdQuery
        }

        confirmVerified(
            spec, select, from, join, where, groupBy, having,
            createdFroms, mergedFroms, createdQuery,
            criteriaQuery, subquery, criteriaBuilder
        )
    }
}
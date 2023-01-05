package com.linecorp.kotlinjdsl.querydsl.where

import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.spec.predicate.AndSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.OrSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class QueryDslImplWhereTest : WithKotlinJdslAssertions {
    @Test
    fun noWhere() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(PredicateSpec.empty)
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(PredicateSpec.empty)
        )
    }

    @Test
    fun nullInWhere() {
        // given
        val predicateSpec: PredicateSpec? = null

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            where(predicateSpec)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(PredicateSpec.empty)
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(PredicateSpec.empty)
        )
    }

    @Test
    fun where() {
        // given
        val predicateSpec: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            where(predicateSpec)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(predicateSpec)
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(predicateSpec)
        )
    }

    @Test
    fun whereAndVararg() {
        // given
        val nullPredicateSpec: PredicateSpec? = null
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            whereAnd(nullPredicateSpec, predicateSpec1, predicateSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )
    }

    @Test
    fun whereAndList() {
        // given
        val nullPredicateSpec: PredicateSpec? = null
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            whereAnd(listOf(nullPredicateSpec, predicateSpec1, predicateSpec2))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )
    }

    @Test
    fun whereOrVararg() {
        // given
        val nullPredicateSpec: PredicateSpec? = null
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            whereOr(nullPredicateSpec, predicateSpec1, predicateSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(OrSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(OrSpec(listOf(predicateSpec1, predicateSpec2)))
        )
    }

    @Test
    fun whereOrList() {
        // given
        val nullPredicateSpec: PredicateSpec? = null
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            whereOr(listOf(nullPredicateSpec, predicateSpec1, predicateSpec2))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(OrSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(OrSpec(listOf(predicateSpec1, predicateSpec2)))
        )
    }

    @Test
    fun wheres() {
        // given
        val nullPredicateSpec: PredicateSpec? = null
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            where(nullPredicateSpec)
            where(predicateSpec1)
            where(predicateSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )
    }

    @Test
    fun allTypeWheres() {
        // given
        val nullPredicateSpec: PredicateSpec? = null
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
            where(nullPredicateSpec)
            where(predicateSpec1)
            whereAnd(nullPredicateSpec, predicateSpec1, predicateSpec2)
            whereOr(nullPredicateSpec, predicateSpec1, predicateSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(
                AndSpec(
                    listOf(
                        predicateSpec1,
                        AndSpec(listOf(predicateSpec1, predicateSpec2)),
                        OrSpec(listOf(predicateSpec1, predicateSpec2))
                    )
                )
            )
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(
                AndSpec(
                    listOf(
                        predicateSpec1,
                        AndSpec(listOf(predicateSpec1, predicateSpec2)),
                        OrSpec(listOf(predicateSpec1, predicateSpec2))
                    )
                )
            )
        )
    }

    class Data1
}

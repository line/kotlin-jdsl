package com.linecorp.kotlinjdsl.spring.data.querydsl.where

import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.spec.predicate.AndSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class SpringDataQueryDslImplWhereTest : WithKotlinJdslAssertions {
    @Test
    fun noWhere() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.where).isEqualTo(
            WhereClause(PredicateSpec.empty)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.where).isEqualTo(
            WhereClause(PredicateSpec.empty)
        )
    }

    @Test
    fun where() {
        // given
        val predicateSpec: PredicateSpec = mockk()

        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.where).isEqualTo(
            WhereClause(predicateSpec)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.where).isEqualTo(
            WhereClause(predicateSpec)
        )
    }

    @Test
    fun wheres() {
        // given
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(distinct = true, Data1::class.java)
            from(entity(Data1::class))
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

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )
    }

    class Data1
}

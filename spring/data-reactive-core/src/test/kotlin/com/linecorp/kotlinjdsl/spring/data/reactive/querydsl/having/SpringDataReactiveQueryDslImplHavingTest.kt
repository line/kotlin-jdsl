package com.linecorp.kotlinjdsl.spring.data.reactive.querydsl.having

import com.linecorp.kotlinjdsl.query.clause.having.HavingClause
import com.linecorp.kotlinjdsl.query.spec.predicate.AndSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataReactiveReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class SpringDataReactiveQueryDslImplHavingTest : WithKotlinJdslAssertions {
    @Test
    fun noHaving() {
        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )
    }

    @Test
    fun having() {
        // given
        val predicateSpec: PredicateSpec = mockk()

        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            having(predicateSpec)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.having).isEqualTo(
            HavingClause(predicateSpec)
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.having).isEqualTo(
            HavingClause(predicateSpec)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )
    }

    @Test
    fun multipleHaving() {
        // given
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            having(predicateSpec1)
            having(predicateSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.having).isEqualTo(
            HavingClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.having).isEqualTo(
            HavingClause(AndSpec(listOf(predicateSpec1, predicateSpec2)))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.having).isEqualTo(
            HavingClause(PredicateSpec.empty)
        )
    }

    class Data1
}

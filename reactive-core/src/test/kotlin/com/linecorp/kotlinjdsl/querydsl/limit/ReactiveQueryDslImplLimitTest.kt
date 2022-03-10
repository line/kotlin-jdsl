package com.linecorp.kotlinjdsl.querydsl.limit

import com.linecorp.kotlinjdsl.query.clause.limit.ReactiveLimitClause
import com.linecorp.kotlinjdsl.querydsl.ReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class ReactiveQueryDslImplLimitTest : WithKotlinJdslAssertions {
    @Test
    fun noLimit() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Any>(offset = null, maxResults = null)
        )
    }

    @Test
    fun offset() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            offset(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Any>(offset = 10, maxResults = null)
        )
    }

    @Test
    fun maxResults() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            maxResults(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Any>(offset = null, maxResults = 10)
        )
    }

    @Test
    fun limitMaxResults() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Any>(offset = null, maxResults = 10)
        )
    }

    @Test
    fun limitOffsetAndMaxResults() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(1, 10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Any>(offset = 1, maxResults = 10)
        )
    }

    class Data1 {
        val id: String = "test"
        val name: String = "test_name"
    }
}

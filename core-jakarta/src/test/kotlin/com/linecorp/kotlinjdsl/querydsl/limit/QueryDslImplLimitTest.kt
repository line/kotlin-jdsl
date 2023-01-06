package com.linecorp.kotlinjdsl.querydsl.limit

import com.linecorp.kotlinjdsl.query.clause.limit.LimitClause
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test
import jakarta.persistence.Query

internal class QueryDslImplLimitTest : WithKotlinJdslAssertions {
    @Test
    fun noLimit() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause<Query>(offset = null, maxResults = null)
        )
    }

    @Test
    fun offset() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            offset(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause<Query>(offset = 10, maxResults = null)
        )
    }

    @Test
    fun maxResults() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            maxResults(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause<Query>(offset = null, maxResults = 10)
        )
    }

    @Test
    fun limitMaxResults() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause<Query>(offset = null, maxResults = 10)
        )
    }

    @Test
    fun limitOffsetAndMaxResults() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(1, 10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause<Query>(offset = 1, maxResults = 10)
        )
    }

    class Data1 {
        val id: String = "test"
        val name: String = "test_name"
    }
}

package com.linecorp.kotlinjdsl.spring.data.reactive.querydsl.limit

import com.linecorp.kotlinjdsl.query.clause.limit.ReactiveLimitClause
import com.linecorp.kotlinjdsl.spring.reactive.query.clause.limit.SpringDataReactivePageableLimitClause
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataReactiveReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import javax.persistence.Query

internal class SpringDataReactiveQueryDslImplLimitTest : WithKotlinJdslAssertions {
    @Test
    fun noLimit() {
        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Query>(offset = null, maxResults = null)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataReactivePageableLimitClause<Query>(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            ReactiveLimitClause.empty
        )
    }

    @Test
    fun offset() {
        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            offset(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Query>(offset = 10, maxResults = null)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataReactivePageableLimitClause<Query>(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            ReactiveLimitClause.empty
        )
    }

    @Test
    fun maxResults() {
        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            maxResults(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Query>(offset = null, maxResults = 10)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataReactivePageableLimitClause<Query>(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            ReactiveLimitClause.empty
        )
    }

    @Test
    fun limitMaxResults() {
        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Query>(offset = null, maxResults = 10)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataReactivePageableLimitClause<Query>(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            ReactiveLimitClause.empty
        )
    }

    @Test
    fun limitOffsetAndMaxResults() {
        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(1, 10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause<Query>(offset = 1, maxResults = 10)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataReactivePageableLimitClause<Query>(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            ReactiveLimitClause.empty
        )
    }

    @Test
    fun pageable() {
        // given
        val pageable = PageRequest.of(1, 10)

        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            this.pageable = pageable
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            ReactiveLimitClause.empty
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataReactivePageableLimitClause<Query>(pageable)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            ReactiveLimitClause.empty
        )
    }

    class Data1
}

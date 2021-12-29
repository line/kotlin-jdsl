package com.linecorp.kotlinjdsl.spring.data.querydsl.limit

import com.linecorp.kotlinjdsl.query.clause.limit.LimitClause
import com.linecorp.kotlinjdsl.spring.data.query.clause.limit.SpringDataPageableLimitClause
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

internal class SpringDataQueryDslImplLimitTest : WithKotlinJdslAssertions {
    @Test
    fun noLimit() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause(offset = null, maxResults = null)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataPageableLimitClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            LimitClause.empty
        )
    }

    @Test
    fun offset() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            offset(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause(offset = 10, maxResults = null)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataPageableLimitClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            LimitClause.empty
        )
    }

    @Test
    fun maxResults() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            maxResults(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause(offset = null, maxResults = 10)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataPageableLimitClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            LimitClause.empty
        )
    }

    @Test
    fun limitMaxResults() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause(offset = null, maxResults = 10)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataPageableLimitClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            LimitClause.empty
        )
    }

    @Test
    fun limitOffsetAndMaxResults() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            limit(1, 10)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause(offset = 1, maxResults = 10)
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataPageableLimitClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            LimitClause.empty
        )
    }

    @Test
    fun pageable() {
        // given
        val pageable = PageRequest.of(1, 10)

        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            this.pageable = pageable
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.limit).isEqualTo(
            LimitClause.empty
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.limit).isEqualTo(
            SpringDataPageableLimitClause(pageable)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.limit).isEqualTo(
            LimitClause.empty
        )
    }

    class Data1
}
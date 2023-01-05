package com.linecorp.kotlinjdsl.query.clause.limit

import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.Query

@ExtendWith(MockKExtension::class)
internal class LimitClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var query: Query

    @Test
    fun apply() {
        // given
        val offset = 10
        val maxResults = 20

        every { query.setFirstResult(10) } returns query
        every { query.setMaxResults(20) } returns query

        // when
        LimitClause<Query>(offset, maxResults).apply(query)

        // then
        verify(exactly = 1) {
            query.firstResult = offset
            query.maxResults = maxResults
        }

        confirmVerified(query)
    }

    @Test
    fun `apply - null`() {
        // given
        val offset: Int? = null
        val maxResults: Int? = null

        // when
        LimitClause<Query>(offset, maxResults).apply(query)

        // then
        confirmVerified(query)
    }
}

package com.linecorp.kotlinjdsl.query.clause.limit

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ReactiveLimitClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var query: ReactiveQuery<Any>

    @Test
    fun apply() {
        // given
        val offset = 10
        val maxResults = 20

        every { query.setFirstResult(10) } returns query
        every { query.setMaxResults(20) } returns query

        // when
        ReactiveLimitClause<Any>(offset, maxResults).apply(query)

        // then
        verify(exactly = 1) {
            query.setFirstResult(offset)
            query.setMaxResults(maxResults)
        }

        confirmVerified(query)
    }

    @Test
    fun `apply - null`() {
        // given
        val offset: Int? = null
        val maxResults: Int? = null

        // when
        ReactiveLimitClause<Any>(offset, maxResults).apply(query)

        // then
        confirmVerified(query)
    }
}

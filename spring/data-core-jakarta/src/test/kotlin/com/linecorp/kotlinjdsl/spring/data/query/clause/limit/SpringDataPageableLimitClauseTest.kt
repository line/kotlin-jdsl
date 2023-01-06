package com.linecorp.kotlinjdsl.spring.data.query.clause.limit

import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import jakarta.persistence.Query

@ExtendWith(MockKExtension::class)
internal class SpringDataPageableLimitClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var query: Query

    @Test
    fun apply() {
        // given
        val pageable = PageRequest.of(1, 10)

        every { query.setFirstResult(10) } returns query
        every { query.setMaxResults(10) } returns query

        // when
        SpringDataPageableLimitClause<Query>(pageable).apply(query)

        // then
        verify(exactly = 1) {
            query.firstResult = 10
            query.maxResults = 10
        }

        confirmVerified(query)
    }

    @Test
    fun `apply - unPaged`() {
        // given
        val pageable = Pageable.unpaged()

        // when
        SpringDataPageableLimitClause<Query>(pageable).apply(query)

        // then
        confirmVerified(query)
    }
}

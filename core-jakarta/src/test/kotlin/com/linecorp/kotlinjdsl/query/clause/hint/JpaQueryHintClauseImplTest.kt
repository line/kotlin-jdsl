package com.linecorp.kotlinjdsl.query.clause.hint

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
internal class JpaQueryHintClauseImplTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var query: Query

    @Test
    fun apply() {
        // given
        val hints = mapOf<String, Any>(
            "cacheable" to true,
            "sql.hint" to "index"
        )

        every { query.setHint(any(), any()) } returns query

        // when
        JpaQueryHintClauseImpl<Query>(hints).apply(query)

        // then
        verify(exactly = 1) {
            query.setHint("cacheable", true)
            query.setHint("sql.hint", "index")
        }

        confirmVerified(query)
    }
}

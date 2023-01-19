package com.linecorp.kotlinjdsl.query.clause.hint

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class JpaReactiveQueryHintClauseImplTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var query: ReactiveQuery<Any>

    @Test
    fun apply() {
        // given
        val hints = mapOf<String, Any>(
            "cacheable" to true,
            "sql.hint" to "index"
        )

        every { query.setQueryHint(any(), any()) } just runs

        // when
        JpaReactiveQueryHintClauseImpl<Any>(hints).apply(query)

        // then
        verify(exactly = 1) {
            query.setQueryHint("cacheable", true)
            query.setQueryHint("sql.hint", "index")
        }

        confirmVerified(query)
    }

    @Test
    fun empty() {
        assertDoesNotThrow { emptySqlHintClause<Any>().apply(Any()) }
    }
}

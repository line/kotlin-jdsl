package com.linecorp.kotlinjdsl.query.clause.hint

import com.linecorp.kotlinjdsl.query.HibernateMutinyReactiveQuery
import com.linecorp.kotlinjdsl.query.ReactiveQuery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class HibernateSqlMutinyReactiveQueryHintClauseTest {
    private val sut = HibernateSqlMutinyReactiveQueryHintClause<Long>(listOf("hint"))

    @Test
    fun applyNormally() {
        val query = mockk<Mutiny.Query<Long>>()
        every { query.setComment("hint") } returns query

        assertDoesNotThrow { sut.apply(HibernateMutinyReactiveQuery(query)) }

        verify {
            query.setComment("hint")
        }
    }

    @Test
    fun `apply nothing if query is not Mutiny-Query`() {
        val query = mockk<ReactiveQuery<Long>> {
            every { unwrap(Mutiny.Query::class) } throws IllegalArgumentException()
        }

        assertDoesNotThrow { sut.apply(query) }

        verify {
            query.unwrap(Mutiny.Query::class)
        }
    }
}

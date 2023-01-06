package com.linecorp.kotlinjdsl.query.clause.hint

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.eclipse.persistence.queries.DatabaseQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import jakarta.persistence.Query

internal class EclipselinkSqlQueryHintClauseTest {
    private val sut = EclipselinkSqlQueryHintClause(listOf("hint"))

    @Test
    fun applyNormally() {
        val persistenceQuery = mockk<Query>()
        val query = mockk<DatabaseQuery>(relaxed = true)
        every { persistenceQuery.unwrap(DatabaseQuery::class.java) } returns query

        assertDoesNotThrow { sut.apply(persistenceQuery) }

        verify {
            query.hintString = "hint"
            persistenceQuery.unwrap(DatabaseQuery::class.java)
        }
    }

    @Test
    fun `apply nothing if query is not DatabaseQuery`() {
        val query = mockk<Query>()

        assertDoesNotThrow { sut.apply(query) }

        verify {
            query.unwrap(DatabaseQuery::class.java)
        }
    }
}

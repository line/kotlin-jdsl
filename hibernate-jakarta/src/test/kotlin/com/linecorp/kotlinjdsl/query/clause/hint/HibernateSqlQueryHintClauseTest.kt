package com.linecorp.kotlinjdsl.query.clause.hint

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.hibernate.query.Query as HibernateQuery
internal class HibernateSqlQueryHintClauseTest {
    private val sut = HibernateSqlQueryHintClause(listOf("hint"))

    @Test
    fun applyNormally() {
        val query = mockk<HibernateQuery<Long>>()
        every { query.addQueryHint("hint") } returns query
        every { query.unwrap(HibernateQuery::class.java) } returns query

        assertDoesNotThrow { sut.apply(query) }

        verify {
            query.addQueryHint("hint")
            query.unwrap(HibernateQuery::class.java)
        }
    }

    @Test
    fun `apply nothing if query is not HibernateQuery`() {
        val query = mockk<HibernateQuery<Long>> {
            every { unwrap(HibernateQuery::class.java) } throws IllegalArgumentException()
        }

        assertDoesNotThrow { sut.apply(query) }

        verify {
            query.unwrap(HibernateQuery::class.java)
        }
    }
}

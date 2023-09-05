package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlMutinyStatelessSessionUtils
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class MutinyStatelessSessionExtensionsTest : WithAssertions {
    @MockK
    private lateinit var session: Mutiny.StatelessSession

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var updateQuery: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery: DeleteQuery<String>

    @MockK
    private lateinit var query: Mutiny.Query<String>

    @MockK
    private lateinit var queryParams: Map<String, Any?>

    @MockK
    private lateinit var context: RenderContext

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlMutinyStatelessSessionUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every { JpqlMutinyStatelessSessionUtils.createQuery(session, selectQuery, context) } returns query

        // when
        val actual = session.createQuery(selectQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery(session, selectQuery, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every { JpqlMutinyStatelessSessionUtils.createQuery(session, selectQuery, queryParams, context) } returns query

        // when
        val actual = session.createQuery(selectQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery(session, selectQuery, queryParams, context)
        }
    }

    @Test
    fun `createQuery() with an update query`() {
        // given
        every { JpqlMutinyStatelessSessionUtils.createQuery(session, updateQuery, context) } returns query

        // when
        val actual = session.createQuery(updateQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery(session, updateQuery, context)
        }
    }

    @Test
    fun `createQuery() with an update query and query params`() {
        // given
        every { JpqlMutinyStatelessSessionUtils.createQuery(session, updateQuery, queryParams, context) } returns query

        // when
        val actual = session.createQuery(updateQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery(session, updateQuery, queryParams, context)
        }
    }

    @Test
    fun `createQuery() with a delete query`() {
        // given
        every { JpqlMutinyStatelessSessionUtils.createQuery(session, deleteQuery, context) } returns query

        // when
        val actual = session.createQuery(deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery(session, deleteQuery, context)
        }
    }

    @Test
    fun `createQuery() with a delete query and query params`() {
        // given
        every { JpqlMutinyStatelessSessionUtils.createQuery(session, deleteQuery, queryParams, context) } returns query

        // when
        val actual = session.createQuery(deleteQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            JpqlMutinyStatelessSessionUtils.createQuery(session, deleteQuery, queryParams, context)
        }
    }
}

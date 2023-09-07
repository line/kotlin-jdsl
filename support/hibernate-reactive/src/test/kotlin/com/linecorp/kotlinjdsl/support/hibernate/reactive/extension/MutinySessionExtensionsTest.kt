package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlMutinySessionUtils
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
class MutinySessionExtensionsTest : WithAssertions {
    @MockK
    private lateinit var session: Mutiny.Session

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var updateQuery: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery: DeleteQuery<String>

    @MockK
    private lateinit var selectionQuery: Mutiny.SelectionQuery<String>

    @MockK
    private lateinit var mutationQuery: Mutiny.MutationQuery

    @MockK
    private lateinit var queryParams: Map<String, Any?>

    @MockK
    private lateinit var context: RenderContext

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlMutinySessionUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every {
            JpqlMutinySessionUtils.createQuery(any(), any<SelectQuery<String>>(), any())
        } returns selectionQuery

        // when
        val actual = session.createQuery(selectQuery, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery)

        verifySequence {
            JpqlMutinySessionUtils.createQuery(session, selectQuery, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every {
            JpqlMutinySessionUtils.createQuery(any(), any<SelectQuery<String>>(), any(), any())
        } returns selectionQuery

        // when
        val actual = session.createQuery(selectQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery)

        verifySequence {
            JpqlMutinySessionUtils.createQuery(session, selectQuery, queryParams, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<UpdateQuery<String>>(), any())
        } returns mutationQuery

        // when
        val actual = session.createMutationQuery(updateQuery, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, updateQuery, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query and query params`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<UpdateQuery<String>>(), any(), any())
        } returns mutationQuery

        // when
        val actual = session.createMutationQuery(updateQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, updateQuery, queryParams, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<DeleteQuery<String>>(), any())
        } returns mutationQuery

        // when
        val actual = session.createMutationQuery(deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, deleteQuery, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query and query params`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<DeleteQuery<String>>(), any(), any())
        } returns mutationQuery

        // when
        val actual = session.createMutationQuery(deleteQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, deleteQuery, queryParams, context)
        }
    }
}

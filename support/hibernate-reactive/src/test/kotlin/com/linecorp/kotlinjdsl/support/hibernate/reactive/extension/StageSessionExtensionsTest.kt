package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlStageSessionUtils
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.hibernate.reactive.stage.Stage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class StageSessionExtensionsTest : WithAssertions {
    @MockK
    private lateinit var session: Stage.Session

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var updateQuery: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery: DeleteQuery<String>

    @MockK
    private lateinit var selectionQuery: Stage.SelectionQuery<String>

    @MockK
    private lateinit var mutationQuery: Stage.MutationQuery

    @MockK
    private lateinit var queryParams: Map<String, Any?>

    @MockK
    private lateinit var context: RenderContext

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlStageSessionUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every { JpqlStageSessionUtils.createQuery(session, selectQuery, context) } returns selectionQuery

        // when
        val actual = session.createQuery(selectQuery, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery)

        verifySequence {
            JpqlStageSessionUtils.createQuery(session, selectQuery, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every { JpqlStageSessionUtils.createQuery(session, selectQuery, queryParams, context) } returns selectionQuery

        // when
        val actual = session.createQuery(selectQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery)

        verifySequence {
            JpqlStageSessionUtils.createQuery(session, selectQuery, queryParams, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query`() {
        // given
        every { JpqlStageSessionUtils.createMutationQuery(session, updateQuery, context) } returns mutationQuery

        // when
        val actual = session.createMutationQuery(updateQuery, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, updateQuery, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query and query params`() {
        // given
        every {
            JpqlStageSessionUtils.createMutationQuery(
                session,
                updateQuery,
                queryParams,
                context,
            )
        } returns mutationQuery

        // when
        val actual = session.createMutationQuery(updateQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, updateQuery, queryParams, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query`() {
        // given
        every { JpqlStageSessionUtils.createMutationQuery(session, deleteQuery, context) } returns mutationQuery

        // when
        val actual = session.createMutationQuery(deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, deleteQuery, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query and query params`() {
        // given
        every {
            JpqlStageSessionUtils.createMutationQuery(
                session = session,
                query = deleteQuery,
                queryParams = queryParams,
                context = context,
            )
        } returns mutationQuery

        // when
        val actual = session.createMutationQuery(deleteQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, deleteQuery, queryParams, context)
        }
    }
}

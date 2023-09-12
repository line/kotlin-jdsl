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
    private lateinit var context: RenderContext

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var updateQuery1: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery1: DeleteQuery<String>

    @MockK
    private lateinit var selectionQuery1: Mutiny.SelectionQuery<String>

    @MockK
    private lateinit var mutationQuery1: Mutiny.MutationQuery

    @MockK
    private lateinit var queryParams1: Map<String, Any?>

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlMutinySessionUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every {
            JpqlMutinySessionUtils.createQuery(any(), any<SelectQuery<String>>(), any())
        } returns selectionQuery1

        // when
        val actual = session.createQuery(selectQuery1, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery1)

        verifySequence {
            JpqlMutinySessionUtils.createQuery(session, selectQuery1, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every {
            JpqlMutinySessionUtils.createQuery(any(), any<SelectQuery<String>>(), any(), any())
        } returns selectionQuery1

        // when
        val actual = session.createQuery(selectQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery1)

        verifySequence {
            JpqlMutinySessionUtils.createQuery(session, selectQuery1, queryParams1, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<UpdateQuery<String>>(), any())
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(updateQuery1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, updateQuery1, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query and query params`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<UpdateQuery<String>>(), any(), any())
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(updateQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, updateQuery1, queryParams1, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<DeleteQuery<String>>(), any())
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(deleteQuery1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, deleteQuery1, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query and query params`() {
        // given
        every {
            JpqlMutinySessionUtils.createMutationQuery(any(), any<DeleteQuery<String>>(), any(), any())
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(deleteQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlMutinySessionUtils.createMutationQuery(session, deleteQuery1, queryParams1, context)
        }
    }
}

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
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
class StageSessionExtensionsTest : WithAssertions {
    @MockK
    private lateinit var session: Stage.Session

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var updateQuery1: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery1: DeleteQuery<String>

    @MockK
    private lateinit var selectionQuery1: Stage.SelectionQuery<String>

    @MockK
    private lateinit var mutationQuery1: Stage.MutationQuery

    @MockK
    private lateinit var queryParams1: Map<String, Any?>

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlStageSessionUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every {
            JpqlStageSessionUtils.createQuery(any(), any<SelectQuery<String>>(), any<KClass<*>>(), any())
        } returns selectionQuery1
        every { selectQuery1.returnType } returns String::class

        // when
        val actual = session.createQuery(selectQuery1, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery1)

        verifySequence {
            selectQuery1.returnType
            JpqlStageSessionUtils.createQuery(session, selectQuery1, String::class, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every {
            JpqlStageSessionUtils.createQuery(any(), any<SelectQuery<String>>(), any(), any<KClass<*>>(), any())
        } returns selectionQuery1
        every { selectQuery1.returnType } returns String::class

        // when
        val actual = session.createQuery(selectQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery1)

        verifySequence {
            selectQuery1.returnType
            JpqlStageSessionUtils.createQuery(session, selectQuery1, queryParams1, String::class, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query`() {
        // given
        every {
            JpqlStageSessionUtils.createMutationQuery(any(), any<UpdateQuery<String>>(), any())
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(updateQuery1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, updateQuery1, context)
        }
    }

    @Test
    fun `createMutationQuery() with an update query and query params`() {
        // given
        every {
            JpqlStageSessionUtils.createMutationQuery(
                any(), any<UpdateQuery<String>>(), any<Map<String, Any?>>(), any(),
            )
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(updateQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, updateQuery1, queryParams1, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query`() {
        // given
        every {
            JpqlStageSessionUtils.createMutationQuery(any(), any<DeleteQuery<String>>(), any())
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(deleteQuery1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, deleteQuery1, context)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query and query params`() {
        // given
        every {
            JpqlStageSessionUtils.createMutationQuery(
                any(), any<DeleteQuery<String>>(), any<Map<String, Any?>>(), any(),
            )
        } returns mutationQuery1

        // when
        val actual = session.createMutationQuery(deleteQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            JpqlStageSessionUtils.createMutationQuery(session, deleteQuery1, queryParams1, context)
        }
    }
}

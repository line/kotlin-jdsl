package com.linecorp.kotlinjdsl.support.hibernate.reactive.extension

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.hibernate.reactive.JpqlStageStatelessSessionUtils
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
class StageStatelessSessionExtensionsTest : WithAssertions {
    @MockK
    private lateinit var session: Stage.StatelessSession

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var updateQuery1: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery1: DeleteQuery<String>

    @MockK
    private lateinit var query1: Stage.Query<String>

    @MockK
    private lateinit var queryParams1: Map<String, Any?>

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlStageStatelessSessionUtils)
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        every {
            JpqlStageStatelessSessionUtils.createQuery(any(), any<SelectQuery<String>>(), any())
        } returns query1

        // when
        val actual = session.createQuery(selectQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlStageStatelessSessionUtils.createQuery(session, selectQuery1, context)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        every {
            JpqlStageStatelessSessionUtils.createQuery(any(), any<SelectQuery<String>>(), any(), any())
        } returns query1

        // when
        val actual = session.createQuery(selectQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlStageStatelessSessionUtils.createQuery(any(), selectQuery1, any(), any())
        }
    }

    @Test
    fun `createQuery() with an update query`() {
        // given
        every {
            JpqlStageStatelessSessionUtils.createQuery(any(), any<UpdateQuery<String>>(), any())
        } returns query1

        // when
        val actual = session.createQuery(updateQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlStageStatelessSessionUtils.createQuery(session, updateQuery1, context)
        }
    }

    @Test
    fun `createQuery() with an update query and query params`() {
        // given
        every {
            JpqlStageStatelessSessionUtils.createQuery(any(), any<UpdateQuery<String>>(), any(), any())
        } returns query1

        // when
        val actual = session.createQuery(updateQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlStageStatelessSessionUtils.createQuery(session, updateQuery1, queryParams1, context)
        }
    }

    @Test
    fun `createQuery() with a delete query`() {
        // given
        every {
            JpqlStageStatelessSessionUtils.createQuery(any(), any<DeleteQuery<String>>(), any())
        } returns query1

        // when
        val actual = session.createQuery(deleteQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlStageStatelessSessionUtils.createQuery(session, deleteQuery1, context)
        }
    }

    @Test
    fun `createQuery() with a delete query and query params`() {
        // given
        every {
            JpqlStageStatelessSessionUtils.createQuery(any(), any<DeleteQuery<String>>(), any(), any())
        } returns query1

        // when
        val actual = session.createQuery(deleteQuery1, queryParams1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            JpqlStageStatelessSessionUtils.createQuery(session, deleteQuery1, queryParams1, context)
        }
    }
}

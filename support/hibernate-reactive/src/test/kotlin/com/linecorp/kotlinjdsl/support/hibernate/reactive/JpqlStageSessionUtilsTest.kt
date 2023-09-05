package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import com.linecorp.kotlinjdsl.support.hibernate.reactive.extension.createMutationQuery
import io.mockk.every
import io.mockk.excludeRecords
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
class JpqlStageSessionUtilsTest : WithAssertions {
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
    private lateinit var renderer: JpqlRenderer

    @MockK
    private lateinit var context: RenderContext

    private val renderedQuery1 = "query"
    private val renderedParam1 = "queryParam1" to "queryParamValue1"
    private val renderedParam2 = "queryParam2" to "queryParamValue2"

    private val queryParam1 = "queryParam1" to "queryParamValue1"
    private val queryParam2 = "queryParam2" to "queryParamValue2"

    @BeforeEach
    @Suppress("UnusedEquals")
    fun setUp() {
        mockkObject(JpqlRendererHolder)

        every { JpqlRendererHolder.get() } returns renderer

        excludeRecords { JpqlRendererHolder.get() }
        excludeRecords { selectionQuery.equals(any()) }
        excludeRecords { mutationQuery.equals(any()) }
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns selectionQuery
        every { selectionQuery.setParameter(any<String>(), any()) } returns selectionQuery

        // when
        val actual = JpqlStageSessionUtils.createQuery(session, selectQuery, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery)

        verifySequence {
            renderer.render(selectQuery, context)
            selectQuery.returnType
            session.createQuery(rendered1.query, String::class.java)
            selectionQuery.setParameter(renderedParam1.first, renderedParam1.second)
            selectionQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns selectionQuery
        every { selectionQuery.setParameter(any<String>(), any()) } returns selectionQuery

        // when
        val actual = JpqlStageSessionUtils
            .createQuery(session, selectQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(selectionQuery)

        verifySequence {
            renderer.render(selectQuery, mapOf(queryParam1, queryParam2), context)
            selectQuery.returnType
            session.createQuery(rendered1.query, String::class.java)
            selectionQuery.setParameter(renderedParam1.first, renderedParam1.second)
            selectionQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createMutationQuery() with an update query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createMutationQuery(any<String>()) } returns mutationQuery
        every { mutationQuery.setParameter(any<String>(), any()) } returns mutationQuery

        // when
        val actual = JpqlStageSessionUtils.createMutationQuery(session, updateQuery, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            renderer.render(updateQuery, context)
            session.createMutationQuery(rendered1.query)
            mutationQuery.setParameter(renderedParam1.first, renderedParam1.second)
            mutationQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createMutationQuery() with an update query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createMutationQuery(any<String>()) } returns mutationQuery
        every { mutationQuery.setParameter(any<String>(), any()) } returns mutationQuery

        // when
        val actual = JpqlStageSessionUtils
            .createMutationQuery(session, updateQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            renderer.render(updateQuery, mapOf(queryParam1, queryParam2), context)
            session.createMutationQuery(rendered1.query)
            mutationQuery.setParameter(renderedParam1.first, renderedParam1.second)
            mutationQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createMutationQuery(any<String>()) } returns mutationQuery
        every { mutationQuery.setParameter(any<String>(), any()) } returns mutationQuery

        // when
        val actual = JpqlStageSessionUtils.createMutationQuery(session, deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            renderer.render(deleteQuery, context)
            session.createMutationQuery(rendered1.query)
            mutationQuery.setParameter(renderedParam1.first, renderedParam1.second)
            mutationQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createMutationQuery() with a delete query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createMutationQuery(any<String>()) } returns mutationQuery
        every { mutationQuery.setParameter(any<String>(), any()) } returns mutationQuery

        // when
        val actual = JpqlStageSessionUtils
            .createMutationQuery(session, deleteQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(mutationQuery)

        verifySequence {
            renderer.render(deleteQuery, mapOf(queryParam1, queryParam2), context)
            session.createMutationQuery(rendered1.query)
            mutationQuery.setParameter(renderedParam1.first, renderedParam1.second)
            mutationQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

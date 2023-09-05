package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
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
    private lateinit var query: Stage.Query<String>

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
        excludeRecords { query.equals(any()) }
    }

    @Test
    fun `createQuery - select query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns query
        every { query.setParameter(any<String>(), any()) } returns query

        // when
        val actual = JpqlStageSessionUtils.createQuery(session, selectQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            renderer.render(selectQuery, context)
            selectQuery.returnType
            session.createQuery(rendered1.query, String::class.java)
            query.setParameter(renderedParam1.first, renderedParam1.second)
            query.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - select query with query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns query
        every { query.setParameter(any<String>(), any()) } returns query

        // when
        val actual = JpqlStageSessionUtils
            .createQuery(session, selectQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            renderer.render(selectQuery, mapOf(queryParam1, queryParam2), context)
            selectQuery.returnType
            session.createQuery(rendered1.query, String::class.java)
            query.setParameter(renderedParam1.first, renderedParam1.second)
            query.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - update query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query
        every { query.setParameter(any<String>(), any()) } returns query

        // when
        val actual = JpqlStageSessionUtils.createQuery(session, updateQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            renderer.render(updateQuery, context)
            session.createQuery<String>(rendered1.query)
            query.setParameter(renderedParam1.first, renderedParam1.second)
            query.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - update query with query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query
        every { query.setParameter(any<String>(), any()) } returns query

        // when
        val actual = JpqlStageSessionUtils
            .createQuery(session, updateQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            renderer.render(updateQuery, mapOf(queryParam1, queryParam2), context)
            session.createQuery<String>(rendered1.query)
            query.setParameter(renderedParam1.first, renderedParam1.second)
            query.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - delete query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query
        every { query.setParameter(any<String>(), any()) } returns query

        // when
        val actual = JpqlStageSessionUtils.createQuery(session, deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            renderer.render(deleteQuery, context)
            session.createQuery<String>(rendered1.query)
            query.setParameter(renderedParam1.first, renderedParam1.second)
            query.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - delete query with query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query
        every { query.setParameter(any<String>(), any()) } returns query

        // when
        val actual = JpqlStageSessionUtils
            .createQuery(session, deleteQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            renderer.render(deleteQuery, mapOf(queryParam1, queryParam2), context)
            session.createQuery<String>(rendered1.query)
            query.setParameter(renderedParam1.first, renderedParam1.second)
            query.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

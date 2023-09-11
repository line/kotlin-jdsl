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
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpqlMutinySessionUtilsTest : WithAssertions {
    @MockK
    private lateinit var session: Mutiny.Session

    @MockK
    private lateinit var renderer: JpqlRenderer

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var updateQuery1: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery1: DeleteQuery<String>

    @MockK
    private lateinit var query1: Mutiny.Query<String>

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
        excludeRecords { query1.equals(any()) }
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery1.returnType } returns String::class
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns query1
        every { query1.setParameter(any<String>(), any()) } returns query1

        // when
        val actual = JpqlMutinySessionUtils.createQuery(session, selectQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            renderer.render(selectQuery1, context)
            selectQuery1.returnType
            session.createQuery(rendered1.query, String::class.java)
            query1.setParameter(renderedParam1.first, renderedParam1.second)
            query1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { selectQuery1.returnType } returns String::class
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns query1
        every { query1.setParameter(any<String>(), any()) } returns query1

        // when
        val actual = JpqlMutinySessionUtils
            .createQuery(session, selectQuery1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            renderer.render(selectQuery1, mapOf(queryParam1, queryParam2), context)
            selectQuery1.returnType
            session.createQuery(rendered1.query, String::class.java)
            query1.setParameter(renderedParam1.first, renderedParam1.second)
            query1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with an update query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query1
        every { query1.setParameter(any<String>(), any()) } returns query1

        // when
        val actual = JpqlMutinySessionUtils.createQuery(session, updateQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            renderer.render(updateQuery1, context)
            session.createQuery<String>(rendered1.query)
            query1.setParameter(renderedParam1.first, renderedParam1.second)
            query1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with an update query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query1
        every { query1.setParameter(any<String>(), any()) } returns query1

        // when
        val actual = JpqlMutinySessionUtils
            .createQuery(session, updateQuery1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            renderer.render(updateQuery1, mapOf(queryParam1, queryParam2), context)
            session.createQuery<String>(rendered1.query)
            query1.setParameter(renderedParam1.first, renderedParam1.second)
            query1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a delete query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query1
        every { query1.setParameter(any<String>(), any()) } returns query1

        // when
        val actual = JpqlMutinySessionUtils.createQuery(session, deleteQuery1, context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            renderer.render(deleteQuery1, context)
            session.createQuery<String>(rendered1.query)
            query1.setParameter(renderedParam1.first, renderedParam1.second)
            query1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a delete query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createQuery<String>(any<String>()) } returns query1
        every { query1.setParameter(any<String>(), any()) } returns query1

        // when
        val actual = JpqlMutinySessionUtils
            .createQuery(session, deleteQuery1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(query1)

        verifySequence {
            renderer.render(deleteQuery1, mapOf(queryParam1, queryParam2), context)
            session.createQuery<String>(rendered1.query)
            query1.setParameter(renderedParam1.first, renderedParam1.second)
            query1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

package com.linecorp.kotlinjdsl.support.hibernate.reactive

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
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
    private lateinit var renderer: JpqlRenderer

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var query1: JpqlQuery<*>

    @MockK
    private lateinit var selectionQuery1: Stage.SelectionQuery<String>

    @MockK
    private lateinit var mutationQuery1: Stage.MutationQuery

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
        excludeRecords { selectionQuery1.equals(any()) }
        excludeRecords { mutationQuery1.equals(any()) }
    }

    @Test
    fun `createQuery() with a query and a return type`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns selectionQuery1
        every { selectionQuery1.setParameter(any<String>(), any()) } returns selectionQuery1

        // when
        val actual = JpqlStageSessionUtils.createQuery(session, query1, String::class, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery1)

        verifySequence {
            renderer.render(query1, context)
            session.createQuery(rendered1.query, String::class.java)
            selectionQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            selectionQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a query and query params and a return type`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createQuery(any<String>(), any<Class<String>>()) } returns selectionQuery1
        every { selectionQuery1.setParameter(any<String>(), any()) } returns selectionQuery1

        // when
        val actual = JpqlStageSessionUtils
            .createQuery(session, query1, mapOf(queryParam1, queryParam2), String::class, context)

        // then
        assertThat(actual).isEqualTo(selectionQuery1)

        verifySequence {
            renderer.render(query1, mapOf(queryParam1, queryParam2), context)
            session.createQuery(rendered1.query, String::class.java)
            selectionQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            selectionQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createMutationQuery() with a query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { session.createMutationQuery(any<String>()) } returns mutationQuery1
        every { mutationQuery1.setParameter(any<String>(), any()) } returns mutationQuery1

        // when
        val actual = JpqlStageSessionUtils.createMutationQuery(session, query1, context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            renderer.render(query1, context)
            session.createMutationQuery(rendered1.query)
            mutationQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            mutationQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createMutationQuery() with a query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { session.createMutationQuery(any<String>()) } returns mutationQuery1
        every { mutationQuery1.setParameter(any<String>(), any()) } returns mutationQuery1

        // when
        val actual = JpqlStageSessionUtils
            .createMutationQuery(session, query1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(mutationQuery1)

        verifySequence {
            renderer.render(query1, mapOf(queryParam1, queryParam2), context)
            session.createMutationQuery(rendered1.query)
            mutationQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            mutationQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

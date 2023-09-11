package com.linecorp.kotlinjdsl.support.eclipselink.javax

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
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpqlEntityManagerUtilsTest : WithAssertions {
    @MockK
    private lateinit var entityManager: EntityManager

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
    private lateinit var stringTypedQuery1: TypedQuery<String>

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
        excludeRecords { stringTypedQuery1.equals(any()) }
    }

    @Test
    fun `createQuery() with a select query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery1.returnType } returns String::class
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, selectQuery1, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(selectQuery1, context)
            selectQuery1.returnType
            entityManager.createQuery(rendered1.query, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a select query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { selectQuery1.returnType } returns String::class
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual =
            JpqlEntityManagerUtils.createQuery(entityManager, selectQuery1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(selectQuery1, mapOf(queryParam1, queryParam2), context)
            selectQuery1.returnType
            entityManager.createQuery(rendered1.query, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with an update query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, updateQuery1, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(updateQuery1, context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with an update query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual =
            JpqlEntityManagerUtils.createQuery(entityManager, updateQuery1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(updateQuery1, mapOf(queryParam1, queryParam2), context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a delete query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery1, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(deleteQuery1, context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a delete query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual =
            JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(deleteQuery1, mapOf(queryParam1, queryParam2), context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

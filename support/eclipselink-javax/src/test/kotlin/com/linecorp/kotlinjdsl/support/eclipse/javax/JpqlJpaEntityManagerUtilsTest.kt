package com.linecorp.kotlinjdsl.support.eclipse.javax

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.junit5.MockKExtension
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.eclipse.persistence.jpa.JpaEntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.TypedQuery

@ExtendWith(MockKExtension::class)
class JpqlJpaEntityManagerUtilsTest : WithAssertions {
    @MockK
    private lateinit var entityManager: JpaEntityManager

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var updateQuery: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery: DeleteQuery<String>

    @MockK
    private lateinit var stringTypedQuery: TypedQuery<String>

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
        excludeRecords { stringTypedQuery.equals(any()) }
    }

    @Test
    fun `createQuery - select query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery

        // when
        val actual = JpqlJpaEntityManagerUtils.createQuery(entityManager, selectQuery, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            renderer.render(selectQuery, context)
            selectQuery.returnType
            entityManager.createQuery(rendered1.query, String::class.java)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - select query with query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery

        // when
        val actual = JpqlJpaEntityManagerUtils
            .createQuery(entityManager, selectQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            renderer.render(selectQuery, mapOf(queryParam1, queryParam2), context)
            selectQuery.returnType
            entityManager.createQuery(rendered1.query, String::class.java)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - update query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery

        // when
        val actual = JpqlJpaEntityManagerUtils.createQuery(entityManager, updateQuery, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            renderer.render(updateQuery, context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - update query with query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery

        // when
        val actual = JpqlJpaEntityManagerUtils
            .createQuery(entityManager, updateQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            renderer.render(updateQuery, mapOf(queryParam1, queryParam2), context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - delete query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery

        // when
        val actual = JpqlJpaEntityManagerUtils.createQuery(entityManager, deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            renderer.render(deleteQuery, context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery - delete query with query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery

        // when
        val actual = JpqlJpaEntityManagerUtils
            .createQuery(entityManager, deleteQuery, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            renderer.render(deleteQuery, mapOf(queryParam1, queryParam2), context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

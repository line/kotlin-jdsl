package com.linecorp.kotlinjdsl.support.hibernate

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
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpqlEntityManagerUtilsTest : WithAssertions {

    private val jpqlEntityManagerUtils = JpqlEntityManagerUtils

    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var updateQuery: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery: DeleteQuery<String>

    @MockK
    private lateinit var queryParams: Map<String, Any?>

    @MockK
    private lateinit var query: Query

    @MockK
    private lateinit var typedQuery: TypedQuery<String>

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var jpqlRenderer: JpqlRenderer

    @MockK
    private lateinit var jpqlRendered: JpqlRendered

    @MockK
    private lateinit var jpqlRenderedParams: JpqlRenderedParams

    private val renderedQuery = "query"
    private val renderedParam = "paramName" to "paramValue"

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlRendererHolder)
        every { JpqlRendererHolder.get() } returns jpqlRenderer
        every { jpqlRendered.query } returns renderedQuery
        every { jpqlRendered.params } returns jpqlRenderedParams
        every { jpqlRenderedParams.entries } returns mapOf(renderedParam).entries
        excludeRecords { JpqlRendererHolder.get() }
        excludeRecords { jpqlRendered.query }
        excludeRecords { jpqlRendered.params }
        excludeRecords { jpqlRenderedParams.entries }
        excludeRecords { query.equals(any()) }
        excludeRecords { typedQuery.equals(any()) }
    }

    @Test
    fun `createQuery - select query`() {
        // given
        every { jpqlRenderer.render(any(), any()) } returns jpqlRendered
        every { selectQuery.returnType } returns String::class
        every { typedQuery.setParameter(any<String>(), any()) } returns typedQuery
        every { entityManager.createQuery(any(), any<Class<*>>()) } returns typedQuery

        // when
        val actual = jpqlEntityManagerUtils.createQuery(entityManager, selectQuery, context)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verifySequence {
            jpqlRenderer.render(selectQuery, context)
            selectQuery.returnType
            entityManager.createQuery(renderedQuery, String::class.java)
            typedQuery.setParameter(renderedParam.first, renderedParam.second)
        }
    }

    @Test
    fun `createQuery - select query with query params`() {
        // given
        every { jpqlRenderer.render(any(), any(), any()) } returns jpqlRendered
        every { selectQuery.returnType } returns String::class
        every { typedQuery.setParameter(any<String>(), any()) } returns typedQuery
        every { entityManager.createQuery(any(), any<Class<*>>()) } returns typedQuery

        // when
        val actual = jpqlEntityManagerUtils.createQuery(entityManager, selectQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verifySequence {
            jpqlRenderer.render(selectQuery, queryParams, context)
            selectQuery.returnType
            entityManager.createQuery(renderedQuery, String::class.java)
            typedQuery.setParameter(renderedParam.first, renderedParam.second)
        }
    }

    @Test
    fun `createQuery - update query`() {
        // given
        every { jpqlRenderer.render(any(), any()) } returns jpqlRendered
        every { query.setParameter(any<String>(), any()) } returns query
        every { entityManager.createQuery(any<String>()) } returns query

        // when
        val actual = jpqlEntityManagerUtils.createQuery(entityManager, updateQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            jpqlRenderer.render(updateQuery, context)
            entityManager.createQuery(renderedQuery)
            query.setParameter(renderedParam.first, renderedParam.second)
        }
    }

    @Test
    fun `createQuery - update query with query params`() {
        // given
        every { jpqlRenderer.render(any(), any(), any()) } returns jpqlRendered
        every { query.setParameter(any<String>(), any()) } returns query
        every { entityManager.createQuery(any<String>()) } returns query

        // when
        val actual = jpqlEntityManagerUtils.createQuery(entityManager, updateQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            jpqlRenderer.render(updateQuery, queryParams, context)
            entityManager.createQuery(renderedQuery)
            query.setParameter(renderedParam.first, renderedParam.second)
        }
    }

    @Test
    fun `createQuery - delete query`() {
        // given
        every { jpqlRenderer.render(any(), any()) } returns jpqlRendered
        every { query.setParameter(any<String>(), any()) } returns query
        every { entityManager.createQuery(any<String>()) } returns query

        // when
        val actual = jpqlEntityManagerUtils.createQuery(entityManager, deleteQuery, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            jpqlRenderer.render(deleteQuery, context)
            entityManager.createQuery(renderedQuery)
            query.setParameter(renderedParam.first, renderedParam.second)
        }
    }

    @Test
    fun `createQuery - delete query with query params`() {
        // given
        every { jpqlRenderer.render(any(), any(), any()) } returns jpqlRendered
        every { query.setParameter(any<String>(), any()) } returns query
        every { entityManager.createQuery(any<String>()) } returns query

        // when
        val actual = jpqlEntityManagerUtils.createQuery(entityManager, deleteQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(query)

        verifySequence {
            jpqlRenderer.render(deleteQuery, queryParams, context)
            entityManager.createQuery(renderedQuery)
            query.setParameter(renderedParam.first, renderedParam.second)
        }
    }
}

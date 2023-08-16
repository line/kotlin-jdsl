package com.linecorp.kotlinjdsl.executor.spring.jpa.javax.jpql

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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.query.QueryUtilsAdaptor
import org.springframework.data.support.PageableExecutionUtilsAdaptor
import java.util.function.LongSupplier
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery

@ExtendWith(MockKExtension::class)
class JpqlEntityManagerUtilsTest : WithAssertions {
    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var updateQuery: UpdateQuery<String>

    @MockK
    private lateinit var deleteQuery: DeleteQuery<String>

    @MockK
    private lateinit var query: Query

    @MockK
    private lateinit var stringTypedQuery: TypedQuery<String>

    @MockK
    private lateinit var longTypedQuery: TypedQuery<Long>

    @MockK
    private lateinit var page: Page<String>

    @MockK
    private lateinit var renderer: JpqlRenderer

    @MockK
    private lateinit var context: RenderContext

    private val renderedQuery1 = "query"
    private val renderedParam1 = "queryParam1" to "queryParamValue1"
    private val renderedParam2 = "queryParam2" to "queryParamValue2"

    private val queryParam1 = "queryParam1" to "queryParamValue1"
    private val queryParam2 = "queryParam2" to "queryParamValue2"

    private val sortedQuery1 = "SELECT 1"
    private val countQuery1 = "SELECT 2"

    @BeforeEach
    @Suppress("UnusedEquals")
    fun setUp() {
        mockkObject(JpqlRendererHolder)
        mockkObject(QueryUtilsAdaptor)
        mockkObject(PageableExecutionUtilsAdaptor)

        every { JpqlRendererHolder.get() } returns renderer

        excludeRecords { JpqlRendererHolder.get() }
        excludeRecords { query.equals(any()) }
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
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, selectQuery, context)

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
        val actual = JpqlEntityManagerUtils
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
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, updateQuery, context)

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
        val actual = JpqlEntityManagerUtils
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
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, deleteQuery, context)

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
        val actual = JpqlEntityManagerUtils
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

    @Test
    fun `queryForPage - select query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        val list1 = listOf("1", "2", "3")
        val pageable1 = PageRequest.of(1, 10)
        val counts1 = listOf(1L, 1L, 1L)

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { QueryUtilsAdaptor.applySorting(any(), any()) } returns sortedQuery1
        every { QueryUtilsAdaptor.createCountQueryFor(any(), any(), any()) } returns countQuery1
        every {
            entityManager.createQuery(any<String>(), any<Class<*>>())
        } returns stringTypedQuery andThen longTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery
        every { stringTypedQuery.setFirstResult(any()) } returns stringTypedQuery
        every { stringTypedQuery.setMaxResults(any()) } returns stringTypedQuery
        every { stringTypedQuery.resultList } returns list1
        every { longTypedQuery.setParameter(any<String>(), any()) } returns longTypedQuery
        every { longTypedQuery.resultList } returns counts1
        every { PageableExecutionUtilsAdaptor.getPage<String>(any(), any(), any()) } answers {
            lastArg<LongSupplier>().asLong

            page
        }

        // when
        val actual = JpqlEntityManagerUtils.queryForPage(entityManager, selectQuery, pageable1, context)

        // then
        assertThat(actual).isEqualTo(page)

        verifySequence {
            renderer.render(selectQuery, context)
            selectQuery.returnType

            QueryUtilsAdaptor.applySorting(rendered1.query, pageable1.sort)
            QueryUtilsAdaptor.createCountQueryFor(rendered1.query, null, false)

            entityManager.createQuery(sortedQuery1, String::class.java)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
            stringTypedQuery.firstResult = pageable1.offset.toInt()
            stringTypedQuery.maxResults = pageable1.pageSize

            entityManager.createQuery(countQuery1, Long::class.javaObjectType)
            longTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            longTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)

            stringTypedQuery.resultList
            PageableExecutionUtilsAdaptor.getPage(list1, pageable1, any())
            longTypedQuery.resultList
        }
    }

    @Test
    fun `queryForPage - select query with query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        val list1 = listOf("1", "2", "3")
        val pageable1 = PageRequest.of(1, 10)
        val counts1 = listOf(1L, 1L, 1L)

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { QueryUtilsAdaptor.applySorting(any(), any()) } returns sortedQuery1
        every { QueryUtilsAdaptor.createCountQueryFor(any(), any(), any()) } returns countQuery1
        every {
            entityManager.createQuery(any<String>(), any<Class<*>>())
        } returns stringTypedQuery andThen longTypedQuery
        every { stringTypedQuery.setParameter(any<String>(), any()) } returns stringTypedQuery
        every { stringTypedQuery.setFirstResult(any()) } returns stringTypedQuery
        every { stringTypedQuery.setMaxResults(any()) } returns stringTypedQuery
        every { stringTypedQuery.resultList } returns list1
        every { longTypedQuery.setParameter(any<String>(), any()) } returns longTypedQuery
        every { longTypedQuery.resultList } returns counts1
        every { PageableExecutionUtilsAdaptor.getPage<String>(any(), any(), any()) } answers {
            lastArg<LongSupplier>().asLong

            page
        }

        // when
        val actual = JpqlEntityManagerUtils
            .queryForPage(entityManager, selectQuery, mapOf(queryParam1, queryParam2), pageable1, context)

        // then
        assertThat(actual).isEqualTo(page)

        verifySequence {
            renderer.render(selectQuery, mapOf(queryParam1, queryParam2), context)
            selectQuery.returnType

            QueryUtilsAdaptor.applySorting(rendered1.query, pageable1.sort)
            QueryUtilsAdaptor.createCountQueryFor(rendered1.query, null, false)

            entityManager.createQuery(sortedQuery1, String::class.java)
            stringTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)
            stringTypedQuery.firstResult = pageable1.offset.toInt()
            stringTypedQuery.maxResults = pageable1.pageSize

            entityManager.createQuery(countQuery1, Long::class.javaObjectType)
            longTypedQuery.setParameter(renderedParam1.first, renderedParam1.second)
            longTypedQuery.setParameter(renderedParam2.first, renderedParam2.second)

            stringTypedQuery.resultList
            PageableExecutionUtilsAdaptor.getPage(list1, pageable1, any())
            longTypedQuery.resultList
        }
    }
}

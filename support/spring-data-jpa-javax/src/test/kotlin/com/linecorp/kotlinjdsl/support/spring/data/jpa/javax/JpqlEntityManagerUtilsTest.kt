package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax

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
import org.springframework.data.domain.SliceImpl
import org.springframework.data.jpa.repository.query.QueryUtilsAdaptor
import org.springframework.data.support.PageableExecutionUtilsAdaptor
import java.util.function.LongSupplier
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

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

    @MockK
    private lateinit var longTypedQuery1: TypedQuery<Long>

    @MockK
    private lateinit var page1: Page<String>

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

    @Test
    fun queryForList() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        val pageable1 = PageRequest.of(1, 10)
        val list1 = listOf("1", "2", "3")

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery1.returnType } returns String::class
        every { QueryUtilsAdaptor.applySorting(any(), any()) } returns sortedQuery1
        every { entityManager.createQuery(any<String>(), any<Class<*>>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultList } returns list1

        // when
        val actual = JpqlEntityManagerUtils.queryForList(entityManager, selectQuery1, pageable1, context)

        // then
        assertThat(actual).isEqualTo(list1)

        verifySequence {
            renderer.render(selectQuery1, context)
            selectQuery1.returnType

            QueryUtilsAdaptor.applySorting(rendered1.query, pageable1.sort)

            entityManager.createQuery(sortedQuery1, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
            stringTypedQuery1.firstResult = pageable1.offset.toInt()
            stringTypedQuery1.maxResults = pageable1.pageSize

            stringTypedQuery1.resultList
        }
    }

    @Test
    fun queryForPage() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        val pageable1 = PageRequest.of(1, 10)
        val list1 = listOf("1", "2", "3")
        val counts1 = listOf(1L, 1L, 1L)

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery1.returnType } returns String::class
        every { QueryUtilsAdaptor.applySorting(any(), any()) } returns sortedQuery1
        every { QueryUtilsAdaptor.createCountQueryFor(any(), any(), any()) } returns countQuery1
        every {
            entityManager.createQuery(any<String>(), any<Class<*>>())
        } returns stringTypedQuery1 andThen longTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultList } returns list1
        every { longTypedQuery1.setParameter(any<String>(), any()) } returns longTypedQuery1
        every { longTypedQuery1.resultList } returns counts1
        every { PageableExecutionUtilsAdaptor.getPage<String>(any(), any(), any()) } answers {
            lastArg<LongSupplier>().asLong

            page1
        }

        // when
        val actual = JpqlEntityManagerUtils.queryForPage(entityManager, selectQuery1, pageable1, context)

        // then
        assertThat(actual).isEqualTo(page1)

        verifySequence {
            renderer.render(selectQuery1, context)
            selectQuery1.returnType

            QueryUtilsAdaptor.applySorting(rendered1.query, pageable1.sort)
            QueryUtilsAdaptor.createCountQueryFor(rendered1.query, null, false)

            entityManager.createQuery(sortedQuery1, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
            stringTypedQuery1.firstResult = pageable1.offset.toInt()
            stringTypedQuery1.maxResults = pageable1.pageSize

            entityManager.createQuery(countQuery1, Long::class.javaObjectType)
            longTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            longTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)

            stringTypedQuery1.resultList
            PageableExecutionUtilsAdaptor.getPage(list1, pageable1, any())
            longTypedQuery1.resultList
        }
    }

    @Test
    fun queryForSlice() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        val pageable1 = PageRequest.of(1, 3)
        val list1 = listOf("1", "2", "3", "4")

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery1.returnType } returns String::class
        every { QueryUtilsAdaptor.applySorting(any(), any()) } returns sortedQuery1
        every { entityManager.createQuery(any<String>(), any<Class<*>>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setFirstResult(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.setMaxResults(any()) } returns stringTypedQuery1
        every { stringTypedQuery1.resultList } returns list1

        // when
        val actual = JpqlEntityManagerUtils.queryForSlice(entityManager, selectQuery1, pageable1, context)

        // then
        assertThat(actual).isEqualTo(SliceImpl(list1.dropLast(1), pageable1, true))

        verifySequence {
            renderer.render(selectQuery1, context)
            selectQuery1.returnType

            QueryUtilsAdaptor.applySorting(rendered1.query, pageable1.sort)

            entityManager.createQuery(sortedQuery1, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
            stringTypedQuery1.firstResult = pageable1.offset.toInt()
            stringTypedQuery1.maxResults = pageable1.pageSize + 1

            stringTypedQuery1.resultList
        }
    }
}

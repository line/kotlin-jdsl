package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax

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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.query.QueryEnhancer
import org.springframework.data.jpa.repository.query.QueryEnhancerFactoryAdaptor
import javax.persistence.EntityManager
import javax.persistence.Parameter
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
    private lateinit var queryEnhancer: QueryEnhancer

    @MockK
    private lateinit var query1: JpqlQuery<*>

    @MockK
    private lateinit var stringTypedQuery1: TypedQuery<String>

    @MockK
    private lateinit var longTypedQuery1: TypedQuery<Long>

    @MockK
    private lateinit var longTypedQueryParam1: Parameter<String>

    @MockK
    private lateinit var longTypedQueryParam2: Parameter<String>

    private val sort1 = Sort.by("property1")

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
        mockkObject(QueryEnhancerFactoryAdaptor)

        every { JpqlRendererHolder.get() } returns renderer

        excludeRecords { JpqlRendererHolder.get() }
        excludeRecords { stringTypedQuery1.equals(any()) }
        excludeRecords { longTypedQuery1.equals(any()) }
        excludeRecords { longTypedQueryParam1.hashCode() }
        excludeRecords { longTypedQueryParam2.hashCode() }
    }

    @Test
    fun `createQuery() with a query and a return type`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, query1, String::class, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(query1, context)
            entityManager.createQuery(rendered1.query, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a query and query params and a return type`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual = JpqlEntityManagerUtils
            .createQuery(entityManager, query1, mapOf(queryParam1, queryParam2), String::class, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(query1, mapOf(queryParam1, queryParam2), context)
            entityManager.createQuery(rendered1.query, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a query`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, query1, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(query1, context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun `createQuery() with a query and query params`() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>()) } returns stringTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1

        // when
        val actual = JpqlEntityManagerUtils
            .createQuery(entityManager, query1, mapOf(queryParam1, queryParam2), context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(query1, mapOf(queryParam1, queryParam2), context)
            entityManager.createQuery(rendered1.query)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }

    @Test
    fun createEnhancedQuery() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any()) } returns rendered1
        every { QueryEnhancerFactoryAdaptor.forQuery(any()) } returns queryEnhancer
        every { queryEnhancer.applySorting(any()) } returns sortedQuery1
        every { queryEnhancer.createCountQueryFor() } returns countQuery1
        every {
            entityManager.createQuery(any<String>(), any<Class<*>>())
        } returns stringTypedQuery1 andThen longTypedQuery1
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1
        every { longTypedQuery1.parameters } returns setOf(longTypedQueryParam1, longTypedQueryParam2)
        every { longTypedQuery1.setParameter(any<String>(), any()) } returns longTypedQuery1
        every { longTypedQueryParam1.name } returns renderedParam1.first
        every { longTypedQueryParam2.name } returns renderedParam2.first

        // when
        val actual = JpqlEntityManagerUtils
            .createEnhancedQuery(entityManager, query1, String::class, sort1, context)

        // then
        assertThat(actual.sortedQuery).isEqualTo(stringTypedQuery1)
        assertThat(actual.countQuery).isEqualTo(longTypedQuery1)

        verifySequence {
            renderer.render(query1, context)

            QueryEnhancerFactoryAdaptor.forQuery(rendered1.query)

            queryEnhancer.applySorting(sort1)
            entityManager.createQuery(sortedQuery1, String::class.java)
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)

            queryEnhancer.createCountQueryFor()
            entityManager.createQuery(countQuery1, Long::class.javaObjectType)
            longTypedQuery1.parameters
            longTypedQueryParam1.name
            longTypedQueryParam2.name
            longTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            longTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

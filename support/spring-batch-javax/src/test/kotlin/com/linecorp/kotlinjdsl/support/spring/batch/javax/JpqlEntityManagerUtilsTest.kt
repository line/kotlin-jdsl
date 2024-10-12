package com.linecorp.kotlinjdsl.support.spring.batch.javax

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
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
    private lateinit var selectQuery1: SelectQuery<String>

    @MockK
    private lateinit var stringTypedQuery1: TypedQuery<String>

    @MockK
    private lateinit var stringTypedQueryParam1: Parameter<String>

    @MockK
    private lateinit var stringTypedQueryParam2: Parameter<String>

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
        excludeRecords { stringTypedQueryParam1.hashCode() }
        excludeRecords { stringTypedQueryParam2.hashCode() }
    }

    @Test
    fun createQuery() {
        // given
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(mapOf(renderedParam1, renderedParam2)))

        every { renderer.render(any(), any(), any()) } returns rendered1
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery1
        every { stringTypedQuery1.parameters } returns setOf(stringTypedQueryParam1, stringTypedQueryParam2)
        every { stringTypedQuery1.setParameter(any<String>(), any()) } returns stringTypedQuery1
        every { stringTypedQueryParam1.name } returns renderedParam1.first
        every { stringTypedQueryParam2.name } returns renderedParam2.first

        // when
        val actual = JpqlEntityManagerUtils
            .createQuery(entityManager, selectQuery1, mapOf(queryParam1, queryParam2), String::class, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            renderer.render(selectQuery1, mapOf(queryParam1, queryParam2), context)
            entityManager.createQuery(rendered1.query, String::class.java)
            stringTypedQuery1.parameters
            stringTypedQueryParam1.name
            stringTypedQueryParam2.name
            stringTypedQuery1.setParameter(renderedParam1.first, renderedParam1.second)
            stringTypedQuery1.setParameter(renderedParam2.first, renderedParam2.second)
        }
    }
}

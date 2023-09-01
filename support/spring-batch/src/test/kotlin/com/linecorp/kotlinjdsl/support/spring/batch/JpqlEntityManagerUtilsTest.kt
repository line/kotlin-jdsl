package com.linecorp.kotlinjdsl.support.spring.batch

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
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpqlEntityManagerUtilsTest : WithAssertions {
    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var query: Query

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
        excludeRecords { query.equals(any()) }
        excludeRecords { stringTypedQuery.equals(any()) }
    }

    @Test
    fun createQuery() {
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
}

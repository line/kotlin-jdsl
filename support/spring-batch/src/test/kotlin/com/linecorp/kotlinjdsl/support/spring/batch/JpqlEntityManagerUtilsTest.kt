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
import jakarta.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Assertions.*
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
    private lateinit var selectQuery: SelectQuery<String>

    @MockK
    private lateinit var stringTypedQuery: TypedQuery<String>

    @MockK
    private lateinit var context: RenderContext

    private val renderedQuery1 = "query"

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlRendererHolder)

        every { JpqlRendererHolder.get() } returns renderer

        excludeRecords { JpqlRendererHolder.get() }
    }

    @Test
    fun createQuery() {
        // given
        val queryParams = emptyMap<String, Any?>()
        val rendered1 = JpqlRendered(renderedQuery1, JpqlRenderedParams(queryParams))

        every { renderer.render(any(), any()) } returns rendered1
        every { selectQuery.returnType } returns String::class
        every { entityManager.createQuery(any<String>(), any<Class<String>>()) } returns stringTypedQuery

        // when
        val actual = JpqlEntityManagerUtils.createQuery(entityManager, selectQuery, queryParams, context)

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            renderer.render(selectQuery, context)
            selectQuery.returnType
            entityManager.createQuery(rendered1.query, String::class.java)
        }
    }
}

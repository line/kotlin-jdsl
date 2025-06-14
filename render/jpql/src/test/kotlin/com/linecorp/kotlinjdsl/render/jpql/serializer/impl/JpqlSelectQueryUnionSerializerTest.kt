package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnion
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@JpqlSerializerTest
@ExtendWith(MockKExtension::class)
class JpqlSelectQueryUnionSerializerTest : WithAssertions {
    private val sut = JpqlSelectQueryUnionSerializer()

    @MockK(relaxUnitFun = true)
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private lateinit var initialContext: RenderContext

    @BeforeEach
    fun setUp() {
        initialContext = TestRenderContext(serializer)
    }

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSelectQueryUnion::class)
    }

    @Test
    fun `serialize operation`() {
        // given
        val leftQuery = mockk<SelectQuery<String>>()
        val rightQuery = mockk<SelectQuery<String>>()
        val part = SelectQueries.selectUnionQuery(
            String::class,
            leftQuery,
            rightQuery,
            orderBy = null,
        ) as JpqlSelectQueryUnion
        val queryContext = initialContext + JpqlRenderStatement.Select
        val slot = slot<() -> Unit>()
        every { leftQuery.toQuery() } returns leftQuery
        every { rightQuery.toQuery() } returns rightQuery

        // when
        sut.serialize(part, writer, initialContext)

        // then
        verify {
            writer.writeParentheses(capture(slot))
        }

        slot.captured.invoke()

        verify {
            writer.writeParentheses(slot.captured)
            serializer.serialize(leftQuery, writer, queryContext)
            writer.write(" UNION ")
            serializer.serialize(rightQuery, writer, queryContext)
        }
    }

    @Test
    fun `serialize operation with order by`() {
        // given
        val leftQuery = mockk<SelectQuery<String>>()
        val rightQuery = mockk<SelectQuery<String>>()
        every { leftQuery.toQuery() } returns leftQuery
        every { rightQuery.toQuery() } returns rightQuery
        val sort = mockk<Sort>()
        val part = SelectQueries.selectUnionQuery(
            returnType = String::class,
            left = leftQuery,
            right = rightQuery,
            orderBy = listOf(sort),
        ) as JpqlSelectQueryUnion
        val queryContext = initialContext + JpqlRenderStatement.Select
        val orderByContext = queryContext + JpqlRenderClause.OrderBy
        val slot = slot<() -> Unit>()

        // when
        sut.serialize(part, writer, initialContext)

        // then
        verify {
            writer.writeParentheses(capture(slot))
        }

        slot.captured.invoke()

        verify {
            writer.writeParentheses(slot.captured)
            serializer.serialize(leftQuery, writer, queryContext)
            writer.write(" UNION ")
            serializer.serialize(rightQuery, writer, queryContext)

            writer.write(" ")
            writer.write("ORDER BY")
            writer.write(" ")
            serializer.serialize(sort, writer, orderByContext)
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.set.SetOperationType
import com.linecorp.kotlinjdsl.querymodel.jpql.set.impl.JpqlSetOperationQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@JpqlSerializerTest
@ExtendWith(MockKExtension::class)
class JpqlSetOperationQuerySerializerTest : WithAssertions {
    private val sut = JpqlSetOperationQuerySerializer()

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
        assertThat(actual).isEqualTo(JpqlSetOperationQuery::class)
    }

    @EnumSource(SetOperationType::class, names = ["UNION", "UNION_ALL"])
    @ParameterizedTest
    fun `serialize operation`(type: SetOperationType) {
        // given
        val leftQuery = mockk<SelectQuery<String>>()
        val rightQuery = mockk<SelectQuery<String>>()
        val part = JpqlSetOperationQuery(leftQuery, type, rightQuery)
        val queryContext = initialContext + JpqlRenderStatement.Select
        val slot = slot<() -> Unit>()

        // when
        sut.serialize(part, writer, initialContext)

        // then
        verify {
            writer.writeParentheses(capture(slot))
            writer.write(" ")
            writer.write(type.value)
            writer.write(" ")
        }

        slot.captured.invoke()

        verify {
            writer.writeParentheses(slot.captured)
            serializer.serialize(leftQuery, writer, queryContext)
            writer.write(" ")
            writer.write(type.value)
            writer.write(" ")
            serializer.serialize(rightQuery, writer, queryContext)
        }
    }

    @EnumSource(SetOperationType::class, names = ["UNION", "UNION_ALL"])
    @ParameterizedTest
    fun `serialize operation with order by`(type: SetOperationType) {
        // given
        val leftQuery = mockk<SelectQuery<String>>()
        val rightQuery = mockk<SelectQuery<String>>()
        val sort = mockk<Sort>()
        val part = JpqlSetOperationQuery(
            leftQuery = leftQuery,
            operationType = type,
            rightQuery = rightQuery,
            orderBy = listOf(sort),
        )
        val queryContext = initialContext + JpqlRenderStatement.Select
        val orderByContext = queryContext + JpqlRenderClause.OrderBy
        val slot = slot<() -> Unit>()

        // when
        sut.serialize(part, writer, initialContext)

        // then
        verify {
            writer.writeParentheses(capture(slot))
            writer.write(" ")
            writer.write(type.value)
            writer.write(" ")

            writer.write(" ")
            writer.write("ORDER BY")
            writer.write(" ")
            serializer.serialize(sort, writer, orderByContext)
        }

        slot.captured.invoke()

        verify {
            writer.writeParentheses(slot.captured)
            serializer.serialize(leftQuery, writer, queryContext)
            writer.write(" ")
            writer.write(type.value)
            writer.write(" ")
            serializer.serialize(rightQuery, writer, queryContext)

            writer.write(" ")
            writer.write("ORDER BY")
            writer.write(" ")
            serializer.serialize(sort, writer, orderByContext)
        }
    }
}

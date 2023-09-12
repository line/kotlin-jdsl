package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAvg
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlAvgSerializerTest : WithAssertions {
    private val sut = JpqlAvgSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::price)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlAvg::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.avg(
            distinct = false,
            expression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAvg<*>, writer, context)

        // then
        verifySequence {
            writer.write("AVG")
            writer.writeParentheses(any())
            serializer.serialize(expression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws the DISTINCT, when the distinct is enabled`() {
        // given
        val part = Expressions.avg(
            distinct = true,
            expression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAvg<*>, writer, context)

        // then
        verifySequence {
            writer.write("AVG")
            writer.writeParentheses(any())
            writer.write("DISTINCT")
            writer.write(" ")
            serializer.serialize(expression1, writer, context)
        }
    }
}

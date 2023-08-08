package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAvg
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JpqlAvgSerializerTest : WithAssertions {
    private val sut = JpqlAvgSerializer()

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlAvg::class)
    }

    @Test
    fun `serialize - WHEN distinct is disabled, THEN draw avg function only`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.avg(false, Expressions.doubleLiteral(3.0))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAvg<*>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("AVG")
            writer.write("(")
            serializer.serialize(part.expr, writer, context)
            writer.write(")")
        }

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }

    @Test
    fun `serialize - WHEN distinct is enabled, THEN draw avg function with distinct`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.avg(true, Expressions.doubleLiteral(3.0))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAvg<*>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("AVG")
            writer.write("(")
            writer.write("DISTINCT")
            writer.write(" ")
            serializer.serialize(part.expr, writer, context)
            writer.write(")")
        }

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }
}

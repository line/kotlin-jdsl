package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSum
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JpqlSumSerializerTest : WithAssertions {
    private val sut = JpqlSumSerializer()

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSum::class)
    }

    @Test
    fun `serialize - WHEN distinct is disabled, THEN draw sum function only`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.sum(false, Expressions.intLiteral(10))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSum<*, *>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("SUM")
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
    fun `serialize - WHEN distinct is enabled, THEN draw sum function with distinct`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.sum(true, Expressions.intLiteral(10))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSum<*, *>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("SUM")
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

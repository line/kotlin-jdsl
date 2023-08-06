package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMax
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMin
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JpqlMinSerializerTest : WithAssertions {
    private val sut = JpqlMinSerializer()

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlMin::class)
    }

    @Test
    fun `serialize - WHEN distinct is disabled, THEN draw min function only`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.min(false, Expressions.stringLiteral("name"))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlMin<*>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("MIN")
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
    fun `serialize - WHEN distinct is enabled, THEN draw min function with distinct`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.min(true, Expressions.stringLiteral("name"))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlMin<*>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("MIN")
            writer.write("(")
            writer.write("DISTINCT")
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

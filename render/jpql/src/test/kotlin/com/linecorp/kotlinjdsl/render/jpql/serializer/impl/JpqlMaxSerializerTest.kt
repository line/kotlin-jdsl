package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlMax
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JpqlMaxSerializerTest : WithAssertions {
    private val sut = JpqlMaxSerializer()

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlMax::class)
    }

    @Test
    fun `serialize - WHEN distinct is disabled, THEN draw max function only`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.max(false, Expressions.stringLiteral("name"))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlMax<*>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("MAX")
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
    fun `serialize - WHEN distinct is enabled, THEN draw max function with distinct`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.max(true, Expressions.stringLiteral("name"))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlMax<*>, writer, context)

        // then
        verify(exactly = 1) {
            writer.write("MAX")
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

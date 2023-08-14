package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNullIf
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlNullIfSerializerTest : WithAssertions {
    private val sut = JpqlNullIfSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNullIf::class)
    }

    @Test
    fun `serialize - NullIf when value and compareValue are the same`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.nullIf(
            Expressions.stringLiteral("name"),
            Expressions.stringLiteral("name"),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNullIf<*>, writer, context)

        // then
        verifySequence {
            writer.write("NULLIF")
            writer.write("(")
            serializer.serialize(part.value, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(part.compareValue, writer, context)
            writer.write(")")
        }
    }

    @Test
    fun `serialize - NullIf when value and compareValue are different`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.nullIf(
            Expressions.intLiteral(10),
            Expressions.intLiteral(20),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNullIf<*>, writer, context)

        // then
        verifySequence {
            writer.write("NULLIF")
            writer.write("(")
            serializer.serialize(part.value, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(part.compareValue, writer, context)
            writer.write(")")
        }
    }
}

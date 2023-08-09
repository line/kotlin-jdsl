package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSum
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
    fun `serialize - WHEN distinct is disabled, THEN draw sum function only`(
        @MockK writer: JpqlWriter,
        @MockK serializer: JpqlRenderSerializer,
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.sum(false, Expressions.intLiteral(10))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSum<*, *>, writer, context)

        // then
        verifySequence {
            writer.write("SUM")
            writer.write("(")
            serializer.serialize(part.expr, writer, context)
            writer.write(")")
        }
    }

    @Test
    fun `serialize - WHEN distinct is enabled, THEN draw sum function with distinct`(
        @MockK writer: JpqlWriter,
        @MockK serializer: JpqlRenderSerializer,
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.sum(true, Expressions.intLiteral(10))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSum<*, *>, writer, context)

        // then
        verifySequence {
            writer.write("SUM")
            writer.write("(")
            writer.write("DISTINCT")
            writer.write(" ")
            serializer.serialize(part.expr, writer, context)
            writer.write(")")
        }
    }
}

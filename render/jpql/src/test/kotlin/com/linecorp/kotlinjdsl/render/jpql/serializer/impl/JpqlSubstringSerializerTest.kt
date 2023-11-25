package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubstring
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlSubstringSerializerTest : WithAssertions {
    private val sut = JpqlSubstringSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val stringExpression1 = Expressions.value("string1")
    private val intExpression1 = Expressions.value(1)
    private val intExpression2 = Expressions.value(1)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSubstring::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.substring(
            value = stringExpression1,
            start = intExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSubstring, writer, context)

        // then
        verifySequence {
            writer.write("SUBSTRING")
            writer.writeParentheses(any())
            serializer.serialize(stringExpression1, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(intExpression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws the comma, when the length is not null`() {
        // given
        val part = Expressions.substring(
            value = stringExpression1,
            start = intExpression1,
            length = intExpression2,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSubstring, writer, context)

        // then
        verifySequence {
            writer.write("SUBSTRING")
            writer.writeParentheses(any())
            serializer.serialize(stringExpression1, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(intExpression1, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(intExpression2, writer, context)
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrim
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlTrimSerializerTest : WithAssertions {
    private val sut = JpqlTrimSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val charExpression1 = Expressions.value('c')
    private val stringExpression1 = Expressions.value("string1")

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlTrim::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.trim(
            value = stringExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlTrim, writer, context)

        // then
        verifySequence {
            writer.write("TRIM")
            writer.writeParentheses(any())
            serializer.serialize(stringExpression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws the character and the FROM, when the character is not null`() {
        // given
        val part = Expressions.trim(
            character = charExpression1,
            value = stringExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlTrim, writer, context)

        // then
        verifySequence {
            writer.write("TRIM")
            writer.writeParentheses(any())
            serializer.serialize(charExpression1, writer, context)
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            serializer.serialize(stringExpression1, writer, context)
        }
    }
}

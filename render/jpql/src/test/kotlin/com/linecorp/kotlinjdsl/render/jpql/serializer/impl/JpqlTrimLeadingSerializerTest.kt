package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimLeading
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlTrimLeadingSerializerTest : WithAssertions {
    private val sut = JpqlTrimLeadingSerializer()

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
        assertThat(actual).isEqualTo(JpqlTrimLeading::class)
    }

    @Test
    fun `serialize() draws the LEADING and the FROM, when the character is null`() {
        // given
        val part = Expressions.trimLeading(
            value = stringExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlTrimLeading, writer, context)

        // then
        verifySequence {
            writer.write("TRIM")
            writer.writeParentheses(any())
            writer.write("LEADING")
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            serializer.serialize(stringExpression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws the LEADING, the character and the FROM, when the character is not null`() {
        // given
        val part = Expressions.trimLeading(
            character = charExpression1,
            value = stringExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlTrimLeading, writer, context)

        // then
        verifySequence {
            writer.write("TRIM")
            writer.writeParentheses(any())
            writer.write("LEADING")
            writer.write(" ")
            serializer.serialize(charExpression1, writer, context)
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            serializer.serialize(stringExpression1, writer, context)
        }
    }
}

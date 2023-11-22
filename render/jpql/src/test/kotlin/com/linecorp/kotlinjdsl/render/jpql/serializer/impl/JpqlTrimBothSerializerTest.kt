package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTrimBoth
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlTrimBothSerializerTest : WithAssertions {
    private val sut = JpqlTrimBothSerializer()

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
        assertThat(actual).isEqualTo(JpqlTrimBoth::class)
    }

    @Test
    fun `serialize() draws the BOTH and the FROM, when the character is null`() {
        // given
        val part = Expressions.trimBoth(
            value = stringExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlTrimBoth, writer, context)

        // then
        verifySequence {
            writer.write("TRIM")
            writer.writeParentheses(any())
            writer.write("BOTH")
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            serializer.serialize(stringExpression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws the BOTH, the character and the FROM, when the character is not null`() {
        // given
        val part = Expressions.trimBoth(
            character = charExpression1,
            value = stringExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlTrimBoth, writer, context)

        // then
        verifySequence {
            writer.write("TRIM")
            writer.writeParentheses(any())
            writer.write("BOTH")
            writer.write(" ")
            serializer.serialize(charExpression1, writer, context)
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            serializer.serialize(stringExpression1, writer, context)
        }
    }
}

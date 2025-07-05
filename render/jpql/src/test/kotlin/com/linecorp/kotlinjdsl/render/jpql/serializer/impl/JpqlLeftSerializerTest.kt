package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLeft
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@JpqlSerializerTest
internal class JpqlLeftSerializerTest {
    private val sut = JpqlLeftSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @MockK
    private lateinit var context: RenderContext

    @Test
    fun `handledType should return Expression`() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLeft::class)
    }

    @Test
    fun `serialize should write LEFT`() {
        // given
        val expression = Expressions.left(Expressions.value("abc"), Expressions.value(1)) as JpqlLeft

        every { context.getValue(JpqlRenderSerializer) } returns serializer

        // when
        sut.serialize(expression, writer, context)

        // then
        verify {
            writer.write("LEFT")
            writer.writeParentheses(any())
            serializer.serialize(expression.value, writer, context)
            writer.write(", ")
            serializer.serialize(expression.length, writer, context)
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCast
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
internal class JpqlCastSerializerTest {
    private val sut = JpqlCastSerializer()

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
        assertThat(actual).isEqualTo(JpqlCast::class)
    }

    @Test
    fun `serialize should write CAST`() {
        // given
        val expression = Expressions.cast(Expressions.value(1), String::class)

        every { context.getValue(JpqlRenderSerializer) } returns serializer

        // when
        sut.serialize(expression as JpqlCast<*>, writer, context)

        // then
        verify {
            writer.write("CAST")
            writer.writeParentheses(any())
            serializer.serialize(expression.value, writer, context)
            writer.write(" AS ")
            writer.write(String::class.simpleName!!)
        }
    }
}

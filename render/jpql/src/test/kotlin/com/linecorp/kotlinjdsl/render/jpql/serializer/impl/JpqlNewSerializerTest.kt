package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNew
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlNewSerializerTest : WithAssertions {
    private val sut = JpqlNewSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Expressions.value(1)
    private val expression2 = Expressions.value(2)

    private data class Row(
        val value1: Int,
        val value2: Int,
    )

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNew::class)
    }

    @Test
    fun serialize() {
        // given
        val expressions = listOf(
            expression1,
            expression2,
        )

        val part = Expressions.new(
            Row::class,
            expressions,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNew<*>, writer, context)

        // then
        verifySequence {
            writer.write("NEW")
            writer.write(" ")
            writer.write(Row::class.java.name)
            writer.writeParentheses(any())
            writer.writeEach(expressions, separator = ", ", any())
            serializer.serialize(expression1, writer, context)
            serializer.serialize(expression2, writer, context)
        }
    }

    @Test
    fun `serialize() draws the NEW without args, when the args is empty`() {
        // given
        val part = Expressions.new(
            Row::class,
            emptyList(),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNew<*>, writer, context)

        // then
        verifySequence {
            writer.write("NEW")
            writer.write(" ")
            writer.write(Row::class.java.name)
            writer.writeParentheses(any())
            writer.writeEach(emptyList<Expression<*>>(), separator = ", ", any())
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFunction
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlFunctionSerializerTest : WithAssertions {
    private val sut = JpqlFunctionSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlFunction::class)
    }

    @Test
    fun `serialize - WHEN args is empty, THEN draw function without args`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.function(
            type = Long::class,
            name = "calculate",
            args = emptyList(),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlFunction<*>, writer, context)

        // then
        verifySequence {
            writer.write("FUNCTION")
            writer.write("(")
            writer.write("calculate")
            writer.write(")")
        }
    }

    @Test
    fun `serialize - WHEN args is not empty, THEN draw function with name and args`() {
        // given
        every { writer.writeEach<Expression<*>>(any(), any(), any(), any(), any()) } answers {
            val expressions: List<Expression<*>> = arg(0)
            val write: (Expression<*>) -> Unit = arg(4)

            expressions.forEach { expression -> write(expression) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.function(
            type = Long::class,
            name = "calculate",
            args = listOf(
                mockkClass(Expression::class) { every { toExpression() } returns mockkClass(Expression::class) },
                mockkClass(Expression::class) { every { toExpression() } returns mockkClass(Expression::class) },
                mockkClass(Expression::class) { every { toExpression() } returns mockkClass(Expression::class) },
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlFunction<*>, writer, context)

        // then
        verifySequence {
            writer.write("FUNCTION")
            writer.write("(")
            writer.write("calculate")
            writer.write(", ")
            writer.write(" ")
            writer.writeEach(part.args, ", ", "", "", any())
            serializer.serialize(part.args.elementAt(0), writer, context)
            serializer.serialize(part.args.elementAt(1), writer, context)
            serializer.serialize(part.args.elementAt(2), writer, context)
            writer.write(")")
        }
    }
}

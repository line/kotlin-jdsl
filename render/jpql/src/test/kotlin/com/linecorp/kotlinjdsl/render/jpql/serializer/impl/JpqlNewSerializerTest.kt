package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNew
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlNewSerializerTest : WithAssertions {
    private val sut = JpqlNewSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNew::class)
    }

    @Test
    fun `serialize - WHEN arguments is empty, THEN draw NEW only`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { writer.writeEach<String>(any(), any(), any(), any(), any()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.new(String::class, emptyList())
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNew<*>, writer, context)

        // then
        verifySequence {
            writer.write("NEW")
            writer.write(" ")

            writer.write(part.type.java.name)

            writer.write("(")

            writer.writeEach(part.args, separator = ", ", prefix = "", postfix = "", any())

            writer.write(")")
        }
    }

    @Test
    fun `serialize - WHEN arguments is not empty, THEN draw arguments with NEW`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { writer.writeEach<Expression<String>>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Expression<String>> = arg(0)
            val write: (Expression<String>) -> Unit = arg(4)

            predicates.forEach { expression -> write(expression) }
        }
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.new(
            String::class,
            listOf(Expressions.stringLiteral("arg1"), Expressions.stringLiteral("arg2")),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNew<*>, writer, context)

        // then
        verifySequence {
            writer.write("NEW")
            writer.write(" ")

            writer.write(part.type.java.name)

            writer.write("(")

            writer.writeEach(part.args, separator = ", ", prefix = "", postfix = "", any())
            serializer.serialize(part.args.elementAt(0), writer, context)
            serializer.serialize(part.args.elementAt(1), writer, context)

            writer.write(")")
        }
    }
}

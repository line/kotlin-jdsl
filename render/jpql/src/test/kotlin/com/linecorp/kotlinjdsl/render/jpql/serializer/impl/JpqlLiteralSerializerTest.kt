package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLiteral
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.BookAuthorType
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
class JpqlLiteralSerializerTest : WithAssertions {
    private val sut = JpqlLiteralSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val int1: Int = 1
    private val long1: Long = 1
    private val float1: Float = 1f
    private val double1: Double = 1.0
    private val char1: Char = 'a'
    private val string1: String = "string1"
    private val string2: String = "string2"
    private val singleQuote: String = "'"

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLiteral::class)
    }

    @Test
    fun `serialize() can draw int`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.intLiteral(int1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.IntLiteral, writer, context)

        // then
        verifySequence {
            writer.write(int1.toString())
        }
    }

    @Test
    fun `serialize() can draw long`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.longLiteral(long1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.LongLiteral, writer, context)

        // then
        verifySequence {
            writer.write(long1.toString())
            writer.write("L")
        }
    }

    @Test
    fun `serialize() can draw float`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.floatLiteral(float1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.FloatLiteral, writer, context)

        // then
        verifySequence {
            writer.write(float1.toString())
            writer.write("F")
        }
    }

    @Test
    fun `serialize() can draw double`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.doubleLiteral(double1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.DoubleLiteral, writer, context)

        // then
        verifySequence {
            writer.write(double1.toString())
        }
    }

    @Test
    fun `serialize() can draw true`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.booleanLiteral(true)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.BooleanLiteral, writer, context)

        // then
        verifySequence {
            writer.write("TRUE")
        }
    }

    @Test
    fun `serialize() can draw false`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.booleanLiteral(false)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.BooleanLiteral, writer, context)

        // then
        verifySequence {
            writer.write("FALSE")
        }
    }

    @Test
    fun `serialize() can draw char`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.charLiteral(char1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.CharLiteral, writer, context)

        // then
        verifySequence {
            writer.write(singleQuote)
            writer.write("$char1")
            writer.write(singleQuote)
        }
    }

    @Test
    fun `serialize() can draw single quote char`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.charLiteral(singleQuote[0])
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.CharLiteral, writer, context)

        // then
        verifySequence {
            writer.write(singleQuote)
            writer.write("$singleQuote$singleQuote")
            writer.write(singleQuote)
        }
    }

    @Test
    fun `serialize() can draw string`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.stringLiteral(string1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.StringLiteral, writer, context)

        // then
        verifySequence {
            writer.write(singleQuote)
            writer.write(string1)
            writer.write(singleQuote)
        }
    }

    @Test
    fun `serialize() can draw string single quote string`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.stringLiteral(string1 + singleQuote + string2)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.StringLiteral, writer, context)

        // then
        verifySequence {
            writer.write(singleQuote)
            writer.write(string1 + singleQuote + singleQuote + string2)
            writer.write(singleQuote)
        }
    }

    @Test
    fun `serialize() can draw enum`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.enumLiteral(BookAuthorType.AUTHOR)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.EnumLiteral, writer, context)

        // then
        verifySequence {
            writer.write(BookAuthorType::class.java.name + "." + BookAuthorType.AUTHOR.name)
        }
    }
}

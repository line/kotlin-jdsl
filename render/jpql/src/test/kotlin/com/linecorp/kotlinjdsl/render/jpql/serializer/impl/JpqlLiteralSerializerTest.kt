package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLiteral
import com.linecorp.kotlinjdsl.render.TestRenderContext
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
    private val boolean1: Boolean = true
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
    fun `serialize - int`() {
        // given
        every { writer.write(any<Int>()) } just runs

        val part = Expressions.intLiteral(int1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.IntLiteral, writer, context)

        // then
        verifySequence {
            writer.write(int1)
        }
    }

    @Test
    fun `serialize - long`() {
        // given
        every { writer.write(any<Long>()) } just runs

        val part = Expressions.longLiteral(long1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.LongLiteral, writer, context)

        // then
        verifySequence {
            writer.write(long1)
        }
    }

    @Test
    fun `serialize - float`() {
        // given
        every { writer.write(any<Float>()) } just runs

        val part = Expressions.floatLiteral(float1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.FloatLiteral, writer, context)

        // then
        verifySequence {
            writer.write(float1)
        }
    }

    @Test
    fun `serialize - double`() {
        // given
        every { writer.write(any<Double>()) } just runs

        val part = Expressions.doubleLiteral(double1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.DoubleLiteral, writer, context)

        // then
        verifySequence {
            writer.write(double1)
        }
    }

    @Test
    fun `serialize - boolean`() {
        // given
        every { writer.write(any<Boolean>()) } just runs

        val part = Expressions.booleanLiteral(boolean1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.BooleanLiteral, writer, context)

        // then
        verifySequence {
            writer.write(boolean1)
        }
    }

    @Test
    fun `serialize - string`() {
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
    fun `serialize - string single quote string`() {
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
}

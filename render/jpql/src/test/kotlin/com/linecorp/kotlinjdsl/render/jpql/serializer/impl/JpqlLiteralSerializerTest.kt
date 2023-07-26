package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLiteral
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JpqlLiteralSerializerTest : WithAssertions {
    private val sut = JpqlLiteralSerializer()

    private val int1: Int = 1
    private val long1: Long = 1
    private val float1: Float = 1f
    private val double1: Double = 1.0
    private val boolean1: Boolean = true
    private val string1: String = "string1"
    private val string2: String = "string2"
    private val singleQuote: String = "'"

    @Test
    fun `serialize int`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<Int>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.intLiteral(int1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.IntLiteral, writer, context)

        // then
        verify(exactly = 1) {
            writer.write(int1)
        }

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }

    @Test
    fun `serialize long`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<Long>()) } just runs
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.longLiteral(long1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.LongLiteral, writer, context)

        // then
        verify(exactly = 1) {
            writer.write(long1)
            writer.write("L")
        }

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }

    @Test
    fun `serialize float`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<Float>()) } just runs
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.floatLiteral(float1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.FloatLiteral, writer, context)

        // then
        verify(exactly = 1) {
            writer.write(float1)
            writer.write("F")
        }

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }

    @Test
    fun `serialize double`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<Double>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.doubleLiteral(double1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.DoubleLiteral, writer, context)

        // then
        verify(exactly = 1) {
            writer.write(double1)
        }

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }

    @Test
    fun `serialize boolean`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val part = Expressions.booleanLiteral(boolean1)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLiteral.BooleanLiteral, writer, context)

        // then
        verify(exactly = 1) {
            writer.write(boolean1.toString().uppercase())
        }

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }

    @Test
    fun `serialize string`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

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

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }

    @Test
    fun `serialize string single quote string`() {
        // given
        val writer = mockkClass(JpqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(JpqlRenderSerializer::class) {
            every { key } returns JpqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

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

        verify {
            serializer.key
        }

        confirmVerified(
            writer,
            serializer,
        )
    }
}

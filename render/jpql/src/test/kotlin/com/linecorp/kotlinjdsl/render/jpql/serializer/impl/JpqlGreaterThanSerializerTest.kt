package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlGreaterThan
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlGreaterThanSerializerTest : WithAssertions {
    private val sut = JpqlGreaterThanSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlGreaterThan::class)
    }

    @Test
    fun `serialize - WHEN greater than to is given, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.greaterThan(
            Expressions.stringLiteral("value"),
            Expressions.stringLiteral("compareValue")
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlGreaterThan<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.value, writer, context)

            writer.write(" ")
            writer.write(">")
            writer.write(" ")

            serializer.serialize(part.compareValue, writer, context)
        }
    }
}

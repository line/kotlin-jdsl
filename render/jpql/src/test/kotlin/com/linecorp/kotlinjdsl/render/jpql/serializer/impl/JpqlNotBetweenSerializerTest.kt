package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotBetween
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
class JpqlNotBetweenSerializerTest : WithAssertions {
    private val sut = JpqlNotBetweenSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNotBetween::class)
    }

    @Test
    fun `serialize - WHEN not between is given, THEN draw not between expression`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.notBetween(
            Expressions.stringLiteral("value"),
            Expressions.stringLiteral("min"),
            Expressions.stringLiteral("max")
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotBetween, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.value, writer, context)
            writer.write(" ")
            writer.write("NOT BETWEEN")
            writer.write(" ")
            serializer.serialize(part.min, writer, context)
            writer.write(" ")
            writer.write("AND")
            writer.write(" ")
            serializer.serialize(part.max, writer, context)
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotLike
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlNotLikeSerializerTest : WithAssertions {
    private val sut = JpqlNotLikeSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNotLike::class)
    }

    @Test
    fun `serialize - WHEN escape is not given, THEN draw only NOT LIKE`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.notLike(
            Expressions.stringLiteral("value"),
            Expressions.stringLiteral("pattern"),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotLike, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.value, writer, context)

            writer.write(" ")
            writer.write("NOT LIKE")
            writer.write(" ")

            serializer.serialize(part.pattern, writer, context)
        }
    }

    @Test
    fun `serialize - WHEN escape is given, THEN draw NOT LIKE with escape`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.notLike(
            Expressions.stringLiteral("value"),
            Expressions.stringLiteral("pattern"),
            Expressions.charLiteral('/'),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotLike, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.value, writer, context)

            writer.write(" ")
            writer.write("NOT LIKE")
            writer.write(" ")

            serializer.serialize(part.pattern, writer, context)

            writer.write(" ")
            writer.write("ESCAPE")
            writer.write(" ")

            serializer.serialize(part.escape!!, writer, context)
        }
    }
}
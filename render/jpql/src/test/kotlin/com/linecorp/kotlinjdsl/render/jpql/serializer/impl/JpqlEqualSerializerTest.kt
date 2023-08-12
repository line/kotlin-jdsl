package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlEqualSerializerTest: WithAssertions {
    val sut = JpqlEqualSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlEqual::class)
    }

    @Test
    fun `serialize - WHEN equal is given, THEN draw equal syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.equal(
            Expressions.stringLiteral("key"),
            Expressions.stringLiteral("value")
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlEqual<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.value, writer, context)

            writer.write(" ")
            writer.write("=")
            writer.write(" ")

            serializer.serialize(part.compareValue, writer, context)
        }
    }
}



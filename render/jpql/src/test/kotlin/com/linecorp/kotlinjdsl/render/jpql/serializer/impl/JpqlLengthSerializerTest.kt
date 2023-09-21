package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLength
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlLengthSerializerTest : WithAssertions {
    private val sut = JpqlLengthSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val stringExpression1 = Expressions.value("string1")

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLength::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.length(
            stringExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLength, writer, context)

        // then
        verifySequence {
            writer.write("LENGTH")
            writer.writeParentheses(any())
            serializer.serialize(stringExpression1, writer, context)
        }
    }
}

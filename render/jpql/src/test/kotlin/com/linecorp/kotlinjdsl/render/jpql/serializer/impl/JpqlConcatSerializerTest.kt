package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlConcat
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlConcatSerializerTest : WithAssertions {
    private val sut = JpqlConcatSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = Expressions.value("string2")
    private val stringExpression3 = Expressions.value("string3")

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlConcat::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.concat(
            stringExpression1,
            stringExpression2,
            listOf(stringExpression3),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlConcat, writer, context)

        // then
        verifySequence {
            writer.write("CONCAT")
            writer.writeParentheses(any())
            writer.writeEach(listOf(stringExpression1, stringExpression2, stringExpression3), ", ", any())
            serializer.serialize(stringExpression1, writer, context)
            serializer.serialize(stringExpression2, writer, context)
            serializer.serialize(stringExpression3, writer, context)
        }
    }
}

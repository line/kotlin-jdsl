package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCoalesce
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlCoalesceSerializerTest : WithAssertions {
    private val sut = JpqlCoalesceSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Expressions.value(1)
    private val expression2 = Expressions.value(2)
    private val expression3 = Expressions.value(3)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlCoalesce::class)
    }

    @Test
    fun serialize() {
        // given
        val coalesce = Expressions.coalesce(
            expression1,
            expression2,
            listOf(expression3),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(coalesce as JpqlCoalesce<*>, writer, context)

        // then
        verifySequence {
            writer.write("COALESCE")
            writer.writeParentheses(any())
            writer.writeEach(listOf(expression1, expression2, expression3), ", ", "", "", any())
            serializer.serialize(expression1, writer, context)
            serializer.serialize(expression2, writer, context)
            serializer.serialize(expression3, writer, context)
        }
    }
}

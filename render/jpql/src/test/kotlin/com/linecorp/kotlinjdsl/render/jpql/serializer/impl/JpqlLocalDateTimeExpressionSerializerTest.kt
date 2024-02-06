package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.LocalDateTimeExpression
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlLocalDateTimeExpressionSerializerTest {
    private val sut = JpqlLocalDateTimeSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun `handle type only JpqlLocalDateTime`() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(LocalDateTimeExpression::class)
    }

    @Test
    fun `serialize LOCAL DATETIME function in Jpql`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.localDateTime() as LocalDateTimeExpression
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part, writer, context)

        // then
        verifySequence {
            writer.write("LOCAL DATETIME")
            writer.writeParentheses(any())
            serializer.serialize(part, writer, context)
        }
    }
}

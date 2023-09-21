package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocate
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlLocateSerializerTest : WithAssertions {
    private val sut = JpqlLocateSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val stringExpression1 = Expressions.value("ok")
    private val stringExpression2 = Paths.path(Book::title)
    private val intExpression1 = Expressions.value(1)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLocate::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.locate(
            substring = stringExpression1,
            string = stringExpression2,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLocate, writer, context)

        // then
        verifySequence {
            writer.write("LOCATE")
            writer.writeParentheses(any())
            serializer.serialize(stringExpression1, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(stringExpression2, writer, context)
        }
    }

    @Test
    fun `serialize() draws the comma, when the start is not null`() {
        // given
        val part = Expressions.locate(
            substring = stringExpression1,
            string = stringExpression2,
            start = intExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLocate, writer, context)

        // then
        verifySequence {
            writer.write("LOCATE")
            writer.writeParentheses(any())
            serializer.serialize(stringExpression1, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(stringExpression2, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(intExpression1, writer, context)
        }
    }
}

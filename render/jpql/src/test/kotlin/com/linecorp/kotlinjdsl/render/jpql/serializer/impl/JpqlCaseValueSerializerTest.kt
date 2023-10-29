package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseValue
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
class JpqlCaseValueSerializerTest : WithAssertions {
    private val sut = JpqlCaseValueSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val stringPath1 = Paths.path(Book::title)

    private val stringExpression1 = Expressions.value("Book01")
    private val stringExpression2 = Expressions.value("Book02")

    private val intExpression1 = Expressions.value(1)
    private val intExpression2 = Expressions.value(2)
    private val intExpression3 = Expressions.value(3)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlCaseValue::class)
    }

    @Test
    fun serialize() {
        // given
        val expressionAndExpressions = mapOf(
            stringExpression1 to intExpression1,
            stringExpression2 to intExpression2,
        )

        val part = Expressions.caseValue(
            value = stringPath1,
            whens = expressionAndExpressions,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCaseValue<*, *>, writer, context)

        // then
        verifySequence {
            writer.write("CASE")
            writer.write(" ")
            serializer.serialize(stringPath1, writer, context)
            writer.write(" ")
            writer.writeEach(expressionAndExpressions.entries, " ", any())
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(stringExpression1, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(intExpression1, writer, context)
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(stringExpression2, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(intExpression2, writer, context)
            writer.write(" ")
            writer.write("END")
        }
    }

    @Test
    fun `serialize() draws the ELSE, when the else is not null`() {
        // given
        val expressionAndExpressions = mapOf(
            stringExpression1 to intExpression1,
            stringExpression2 to intExpression2,
        )

        val part = Expressions.caseValue(
            value = stringPath1,
            whens = expressionAndExpressions,
            `else` = intExpression3,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCaseValue<*, *>, writer, context)

        // then
        verifySequence {
            writer.write("CASE")
            writer.write(" ")
            serializer.serialize(stringPath1, writer, context)
            writer.write(" ")
            writer.writeEach(expressionAndExpressions.entries, " ", any())
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(stringExpression1, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(intExpression1, writer, context)
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(stringExpression2, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(intExpression2, writer, context)
            writer.write(" ")
            writer.write("ELSE")
            writer.write(" ")
            serializer.serialize(intExpression3, writer, context)
            writer.write(" ")
            writer.write("END")
        }
    }
}

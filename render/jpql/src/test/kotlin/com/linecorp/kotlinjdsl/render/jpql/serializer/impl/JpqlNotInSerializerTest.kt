package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotIn
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@JpqlSerializerTest
class JpqlNotInSerializerTest : WithAssertions {
    private val sut = JpqlNotInSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::price)
    private val expression2 = Expressions.value(BigDecimal.valueOf(100))
    private val expression3 = Expressions.value(BigDecimal.valueOf(200))
    private val expression4 = Expressions.value(BigDecimal.valueOf(300))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNotIn::class)
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are basic types`() {
        // given
        val expressions =
            listOf(
                expression2,
                expression3,
                expression4,
            )

        val part =
            Predicates.notIn(
                expression1,
                expressions,
            )
        val context = TestRenderContext(serializer)

        val singleParam =
            Expressions.value(
                listOf(
                    BigDecimal.valueOf(100),
                    BigDecimal.valueOf(200),
                    BigDecimal.valueOf(300),
                ),
            )

        // when
        sut.serialize(part as JpqlNotIn<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("NOT IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws parameters, when the compareValues contain non-basic types`() {
        // given
        val expressions =
            listOf(
                expression2,
                expression1,
            )

        val part =
            Predicates.notIn(
                expression1,
                expressions,
            )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotIn<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("NOT IN")
            writer.write(" ")
            writer.writeParentheses(any())
            writer.writeEach(expressions, ", ", any())
            serializer.serialize(expression2, writer, context)
            serializer.serialize(expression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws parameters, when the compareValues are not basic types`() {
        // given
        val expressions =
            listOf(
                expression1,
            )

        val part =
            Predicates.notIn(
                expression1,
                expressions,
            )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotIn<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("NOT IN")
            writer.write(" ")
            writer.writeParentheses(any())
            writer.writeEach(expressions, ", ", any())
            serializer.serialize(expression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws 1 = 1, when the compareValues is empty`() {
        // Given
        val part =
            Predicates.notIn(
                expression1,
                emptyList(),
            )
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlNotIn<*>, writer, context)

        // Then
        verifySequence {
            writer.write("1 = 1")
        }
    }
}

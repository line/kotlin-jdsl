package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIn
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.called
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlInSerializerTest : WithAssertions {
    private val sut = JpqlInSerializer()

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
        assertThat(actual).isEqualTo(JpqlIn::class)
    }

    @Test
    fun serialize() {
        // given
        val expressions = listOf(
            expression2,
            expression3,
            expression4,
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlIn<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            writer.writeEach(expressions, ", ", "", "", any())
            serializer.serialize(expression2, writer, context)
            serializer.serialize(expression3, writer, context)
            serializer.serialize(expression4, writer, context)
        }
    }

    @Test
    fun `serialize() draws nothing, when the compareValues is empty`() {
        // Given
        val part = Predicates.`in`(
            expression1,
            emptyList(),
        )
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            writer wasNot called
        }
    }
}

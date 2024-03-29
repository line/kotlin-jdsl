package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlBetween
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
class JpqlBetweenSerializerTest : WithAssertions {
    private val sut = JpqlBetweenSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::price)
    private val expression2 = Expressions.value(BigDecimal.ZERO)
    private val expression3 = Expressions.value(BigDecimal.valueOf(100))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlBetween::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.between(
            expression1,
            expression2,
            expression3,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlBetween, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("BETWEEN")
            writer.write(" ")
            serializer.serialize(expression2, writer, context)
            writer.write(" ")
            writer.write("AND")
            writer.write(" ")
            serializer.serialize(expression3, writer, context)
        }
    }
}

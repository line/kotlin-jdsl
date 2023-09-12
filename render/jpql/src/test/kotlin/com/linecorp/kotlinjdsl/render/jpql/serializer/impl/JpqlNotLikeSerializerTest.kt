package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotLike
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
class JpqlNotLikeSerializerTest : WithAssertions {
    private val sut = JpqlNotLikeSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val stringExpression1 = Paths.path(Book::title)
    private val stringExpression2 = Expressions.value("pattern")

    private val charExpression1 = Expressions.value('_')

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNotLike::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.notLike(
            stringExpression1,
            stringExpression2,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotLike, writer, context)

        // then
        verifySequence {
            serializer.serialize(stringExpression1, writer, context)
            writer.write(" ")
            writer.write("NOT LIKE")
            writer.write(" ")
            serializer.serialize(stringExpression2, writer, context)
        }
    }

    @Test
    fun `serialize() draws the ESCAPE, when the escape is not null`() {
        // given
        val part = Predicates.notLike(
            stringExpression1,
            stringExpression2,
            charExpression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotLike, writer, context)

        // then
        verifySequence {
            serializer.serialize(stringExpression1, writer, context)
            writer.write(" ")
            writer.write("NOT LIKE")
            writer.write(" ")
            serializer.serialize(stringExpression2, writer, context)
            writer.write(" ")
            writer.write("ESCAPE")
            writer.write(" ")
            serializer.serialize(charExpression1, writer, context)
        }
    }
}

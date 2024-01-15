package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlCustomPredicate
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
class JpqlCustomPredicateSerializerTest : WithAssertions {
    private val sut = JpqlCustomPredicateSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::price)
    private val expression2 = Paths.path(Book::salePrice)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlCustomPredicate::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.customPredicate(
            "TEST({0}, {1})",
            listOf(
                expression1,
                expression2,
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCustomPredicate, writer, context)

        // then
        verifySequence {
            writer.write("TEST(")
            serializer.serialize(expression1, writer, context)
            writer.write(", ")
            serializer.serialize(expression2, writer, context)
            writer.write(")")
        }
    }

    @Test
    fun `serialize() draws the same expression, when the argument number is the same`() {
        // given
        val part = Predicates.customPredicate(
            "TEST({0}, {0})",
            listOf(
                expression1,
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCustomPredicate, writer, context)

        // then
        verifySequence {
            writer.write("TEST(")
            serializer.serialize(expression1, writer, context)
            writer.write(", ")
            serializer.serialize(expression1, writer, context)
            writer.write(")")
        }
    }

    @Test
    fun `serialize() draws only the template, when there are no the argument numbers`() {
        // given
        val part = Predicates.customPredicate(
            "TEST()",
            emptyList(),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCustomPredicate, writer, context)

        // then
        verifySequence {
            writer.write("TEST()")
        }
    }

    @Test
    fun `serialize() draws well, even if there are no parentheses`() {
        // given
        val part = Predicates.customPredicate(
            "{0} = {0}",
            listOf(
                expression1,
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCustomPredicate, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" = ")
            serializer.serialize(expression1, writer, context)
        }
    }
}

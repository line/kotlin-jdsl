package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCaseWhen
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
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
class JpqlCaseWhenSerializerTest : WithAssertions {
    private val sut = JpqlCaseWhenSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val predicate1 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book01"))
    private val predicate2 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book02"))

    private val expression1 = Expressions.value(1)
    private val expression2 = Expressions.value(2)
    private val expression3 = Expressions.value(3)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlCaseWhen::class)
    }

    @Test
    fun serialize() {
        // given
        val predicateAndExpressions = mapOf(
            predicate1 to expression1,
            predicate2 to expression2,
        )

        val part = Expressions.caseWhen(
            whens = predicateAndExpressions,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCaseWhen<*>, writer, context)

        // then
        verifySequence {
            writer.write("CASE")
            writer.write(" ")
            writer.writeEach(predicateAndExpressions.entries, " ", any())
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(predicate1, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(expression1, writer, context)
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(predicate2, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(expression2, writer, context)
            writer.write(" ")
            writer.write("END")
        }
    }

    @Test
    fun `serialize() draws the ELSE, when the else is not null`() {
        // given
        val predicateAndExpressions = mapOf(
            predicate1 to expression1,
            predicate2 to expression2,
        )

        val part = Expressions.caseWhen(
            whens = predicateAndExpressions,
            `else` = expression3,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCaseWhen<*>, writer, context)

        // then
        verifySequence {
            writer.write("CASE")
            writer.write(" ")
            writer.writeEach(predicateAndExpressions.entries, " ", any())
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(predicate1, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(expression1, writer, context)
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(predicate2, writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(expression2, writer, context)
            writer.write(" ")
            writer.write("ELSE")
            writer.write(" ")
            serializer.serialize(expression3, writer, context)
            writer.write(" ")
            writer.write("END")
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlPredicateParentheses
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
class JpqlPredicateParenthesesSerializerTest : WithAssertions {
    private val sut = JpqlPredicateParenthesesSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val predicate1 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book01"))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlPredicateParentheses::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.parentheses(
            predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlPredicateParentheses, writer, context)

        // then
        verifySequence {
            writer.writeParentheses(any())
            serializer.serialize(predicate1, writer, context)
        }
    }
}

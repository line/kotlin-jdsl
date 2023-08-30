package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlInSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
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
class JpqlInSubquerySerializerTest : WithAssertions {
    private val sut = JpqlInSubquerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::title)

    private val subquery1 = Expressions.subquery(
        SelectQueries.select(
            returnType = String::class,
            distinct = false,
            select = listOf(Paths.path(Book::title)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlInSubquery::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.`in`(
            expression1,
            subquery1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlInSubquery<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(subquery1, writer, context)
        }
    }
}

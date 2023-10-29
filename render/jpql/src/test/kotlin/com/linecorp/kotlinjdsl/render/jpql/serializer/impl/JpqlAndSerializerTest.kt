package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlAnd
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
class JpqlAndSerializerTest : WithAssertions {
    private val sut = JpqlAndSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val predicate1 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book01"))
    private val predicate2 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book02"))
    private val predicate3 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book03"))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlAnd::class)
    }

    @Test
    fun serialize() {
        // given
        val predicates = listOf(
            predicate1,
            predicate2,
            predicate3,
        )

        val part = Predicates.and(
            predicates,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAnd, writer, context)

        // then
        verifySequence {
            writer.writeEach(predicates, " AND ", any())
            serializer.serialize(predicate1, writer, context)
            serializer.serialize(predicate2, writer, context)
            serializer.serialize(predicate3, writer, context)
        }
    }

    @Test
    fun `serialize() draws 1 = 1, when the predicates is empty`() {
        // given
        val part = Predicates.and(
            emptyList(),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAnd, writer, context)

        // then
        verifySequence {
            writer.write("1 = 1")
        }
    }
}

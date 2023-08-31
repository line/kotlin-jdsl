package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlDeleteQuerySerializerTest : WithAssertions {
    private val sut = JpqlDeleteQuerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entity1 = Entities.entity(Book::class)

    private val predicate1 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book01"))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlDeleteQuery::class)
    }

    @Test
    fun serialize() {
        // given
        val part = DeleteQueries.deleteQuery(
            entity = entity1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlDeleteQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("DELETE")
            writer.write(" ")
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Delete + JpqlRenderClause.DeleteFrom,
            )
        }
    }

    @Test
    fun `serialize() draws the WHERE, when the where is not null`() {
        // given
        val part = DeleteQueries.deleteQuery(
            entity = entity1,
            where = predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlDeleteQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("DELETE")
            writer.write(" ")
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Delete + JpqlRenderClause.DeleteFrom,
            )
            writer.write(" ")
            writer.write("WHERE")
            writer.write(" ")
            serializer.serialize(
                predicate1,
                writer,
                context + JpqlRenderStatement.Delete + JpqlRenderClause.Where,
            )
        }
    }
}

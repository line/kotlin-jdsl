package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftAssociationFetchJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlLeftAssociationFetchJoinSerializerTest : WithAssertions {
    private val sut = JpqlLeftAssociationFetchJoinSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entity1 = Entities.entity(Book::class, "book01")

    private val path1 = Paths.path(entity1, Book::authors)

    private val predicate1 = Predicates.equal(
        Paths.path(entity1, Book::title),
        Expressions.value("Book01"),
    )

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLeftAssociationFetchJoin::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Joins.leftFetchJoin(
            entity1,
            path1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLeftAssociationFetchJoin<*>, writer, context)

        // then
        verifySequence {
            writer.write("LEFT JOIN FETCH")
            writer.write(" ")
            serializer.serialize(path1, writer, context + JpqlRenderClause.Join)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(entity1.alias)
        }
    }

    @Test
    fun `serialize() draws the ON, when the predicate is not null`() {
        // given
        val part = Joins.leftFetchJoin(
            entity1,
            path1,
            predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLeftAssociationFetchJoin<*>, writer, context)

        // then
        verifySequence {
            writer.write("LEFT JOIN FETCH")
            writer.write(" ")
            serializer.serialize(path1, writer, context + JpqlRenderClause.Join)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(entity1.alias)
            writer.write(" ")
            writer.write("ON")
            writer.write(" ")
            serializer.serialize(predicate1, writer, context + JpqlRenderClause.On)
        }
    }
}

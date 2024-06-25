package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.render.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlLeftJoinSerializerTest : WithAssertions {
    private val sut = JpqlLeftJoinSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entity1 = Entities.entity(Author::class, "author01")

    private val predicate1 = Predicates.equal(
        Paths.path(entity1, Author::authorId),
        Paths.path(BookAuthor::authorId),
    )

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLeftJoin::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Joins.leftJoin(
            entity1,
            predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLeftJoin<*>, writer, context)

        // then
        verifySequence {
            writer.write("LEFT JOIN")
            writer.write(" ")
            serializer.serialize(entity1, writer, context + JpqlRenderClause.Join)
            writer.write(" ")
            writer.write("ON")
            writer.write(" ")
            serializer.serialize(predicate1, writer, context + JpqlRenderClause.On)
        }
    }
}

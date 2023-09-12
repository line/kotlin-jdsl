package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerFetchJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.render.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlInnerFetchJoinSerializerTest : WithAssertions {
    private val sut = JpqlInnerFetchJoinSerializer()

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
        assertThat(actual).isEqualTo(JpqlInnerFetchJoin::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Joins.innerFetchJoin(
            entity1,
            predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlInnerFetchJoin<*>, writer, context)

        // then
        verifySequence {
            writer.write("INNER JOIN FETCH")
            writer.write(" ")
            serializer.serialize(part.entity, writer, context)
            writer.write(" ")
            writer.write("ON")
            writer.write(" ")
            serializer.serialize(part.on, writer, context)
        }
    }
}

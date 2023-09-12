package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Froms
import com.linecorp.kotlinjdsl.querymodel.jpql.from.impl.JpqlJoinedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlJoinedEntitySerializerTest : WithAssertions {
    private val sut = JpqlJoinedEntitySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entity1 = Entities.entity(Book::class)

    private val join1 = Joins.innerJoin(Entities.entity(BookAuthor::class), Paths.path(Book::authors))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlJoinedEntity::class)
    }

    @Test
    fun serialize() {
        // given
        val parts = Froms.reduce(
            listOf(
                entity1,
                join1,
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        parts.forEach {
            sut.serialize(it as JpqlJoinedEntity, writer, context)
        }

        // then
        verifySequence {
            serializer.serialize(entity1, writer, context)
            writer.write(" ")
            serializer.serialize(join1, writer, context)
        }
    }
}

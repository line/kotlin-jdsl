package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIndex
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlIndexSerializerTest : WithAssertions {
    private val sut = JpqlIndexSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entity1 = Entities.entity(BookAuthor::class)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlIndex::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.index(
            entity = entity1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlIndex, writer, context)

        // then
        verifySequence {
            writer.write("INDEX")
            writer.writeParentheses(any())
            serializer.serialize(entity1, writer, context)
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsEmpty
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
class JpqlIsEmptySerializerTest : WithAssertions {
    private val sut = JpqlIsEmptySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val path1 = Paths.path(Book::authors)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlIsEmpty::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.isEmpty(
            path1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlIsEmpty<*, *>, writer, context)

        // then
        verifySequence {
            serializer.serialize(path1, writer, context)
            writer.write(" ")
            writer.write("IS EMPTY")
        }
    }
}

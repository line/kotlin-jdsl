package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNotEmpty
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
class JpqlIsNotEmptySerializerTest : WithAssertions {
    private val sut = JpqlIsNotEmptySerializer()

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
        assertThat(actual).isEqualTo(JpqlIsNotEmpty::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.isNotEmpty(
            path1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlIsNotEmpty<*, *>, writer, context)

        // then
        verifySequence {
            serializer.serialize(path1, writer, context)
            writer.write(" ")
            writer.write("IS NOT EMPTY")
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort
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
class JpqlSortSerializerTest : WithAssertions {
    private val sut = JpqlSortSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::isbn)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSort::class)
    }

    @Test
    fun `serialize() can draw the asc`() {
        // given
        val part = Sorts.asc(
            expression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSort, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("ASC")
        }
    }

    @Test
    fun `serialize() can draw the desc`() {
        // given
        val part = Sorts.desc(
            expression1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSort, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("DESC")
        }
    }

    @Test
    fun `serialize() can draw the asc and nullsFirst`() {
        // given
        val part = Sorts.asc(
            expression1,
            Sort.NullOrder.FIRST,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSort, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("ASC")
            writer.write(" ")
            writer.write("NULLS FIRST")
        }
    }

    @Test
    fun `serialize() can draw the asc and nullsLast`() {
        // given
        val part = Sorts.asc(
            expression1,
            Sort.NullOrder.LAST,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSort, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("ASC")
            writer.write(" ")
            writer.write("NULLS LAST")
        }
    }

    @Test
    fun `serialize() can draw the desc and nullsFirst`() {
        // given
        val part = Sorts.desc(
            expression1,
            Sort.NullOrder.FIRST,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSort, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("DESC")
            writer.write(" ")
            writer.write("NULLS FIRST")
        }
    }

    @Test
    fun `serialize() can draw the desc and nullsLast`() {
        // given
        val part = Sorts.desc(
            expression1,
            Sort.NullOrder.LAST,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSort, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("DESC")
            writer.write(" ")
            writer.write("NULLS LAST")
        }
    }
}

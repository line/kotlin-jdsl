package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlModulo
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
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
class JpqlModuloSerializerTest : WithAssertions {
    private val sut = JpqlModuloSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::price)
    private val expression2 = Paths.path(Book::salePrice)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlModulo::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.mod(
            expression1,
            expression2,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlModulo<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("%")
            writer.write(" ")
            serializer.serialize(expression2, writer, context)
        }
    }
}

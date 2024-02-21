package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPower
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlPowerSerializerTest {
    private val sut = JpqlPowerSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::verticalLength)
    private val expression2 = Expressions.value(2)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlPower::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.round(
            value = expression1,
            scale = expression2,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlPower<*>, writer, context)

        // then
        verifySequence {
            writer.write("POWER")
            writer.writeParentheses(any())
            serializer.serialize(expression1, writer, context)
            writer.write(",")
            writer.write(" ")
            serializer.serialize(expression2, writer, context)
        }
    }
}

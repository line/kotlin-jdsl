package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlTimes
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlTimesSerializerTest : WithAssertions {
    private val sut = JpqlTimesSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlTimes::class)
    }

    @Test
    fun `serialize - WHEN times is disabled, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.times(
            Expressions.intLiteral(10),
            Expressions.intLiteral(20),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlTimes<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.value1, writer, context)

            writer.write(" ")
            writer.write("*")
            writer.write(" ")

            serializer.serialize(part.value2, writer, context)
        }
    }
}

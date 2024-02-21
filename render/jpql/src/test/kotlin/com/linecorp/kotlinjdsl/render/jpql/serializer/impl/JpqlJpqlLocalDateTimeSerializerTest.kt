package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLocalDateTime
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlJpqlLocalDateTimeSerializerTest {
    private val sut = JpqlLocalDateTimeSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handle() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLocalDateTime::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.localDateTime() as JpqlLocalDateTime
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part, writer, context)

        // then
        verifySequence {
            writer.write("LOCAL DATETIME")
        }
    }
}

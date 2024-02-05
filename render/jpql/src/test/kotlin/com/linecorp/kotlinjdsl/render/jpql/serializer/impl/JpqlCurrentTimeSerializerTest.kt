package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions.currentTime
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCurrentTime
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlCurrentTimeSerializerTest : WithAssertions {

    private val sut = JpqlCurrentTimeSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression = currentTime()

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlCurrentTime::class)
    }

    @Test
    fun serialize() {
        // given
        val part = currentTime()
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCurrentTime, writer, context)

        // then
        verifySequence {
            writer.write("CURRENT_TIME")
        }
    }
}

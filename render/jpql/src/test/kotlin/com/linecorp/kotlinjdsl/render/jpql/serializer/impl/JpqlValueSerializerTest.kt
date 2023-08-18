package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.render.TestRenderContext
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
class JpqlValueSerializerTest : WithAssertions {
    private val sut = JpqlValueSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlValue::class)
    }

    @Test
    fun `serialize - WHEN value is disabled, THEN draw full syntax`() {
        // given
        every { writer.writeParam(any()) } just runs

        val part = Expressions.value("value")
        val context = TestRenderContext()

        // when
        sut.serialize(part as JpqlValue<*>, writer, context)

        // then
        verifySequence {
            writer.writeParam(part.value)
        }
    }
}

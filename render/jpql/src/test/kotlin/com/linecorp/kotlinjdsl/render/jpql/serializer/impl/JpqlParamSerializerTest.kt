package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlParam
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlParamSerializerTest : WithAssertions {
    private val sut = JpqlParamSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlParam::class)
    }

    @Test
    fun `serialize - WHEN param is given, THEN draw full syntax`() {
        // given
        every { writer.writeParam(any(), any()) } just runs

        val part = Expressions.param("name", "value")
        val context = TestRenderContext()

        // when
        sut.serialize(part as JpqlParam<*>, writer, context)

        // then
        verifySequence {
            writer.writeParam(part.name, part.value)
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlParam
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlParamSerializerTest : WithAssertions {
    private val sut = JpqlParamSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    private val paramName1 = "paramName1"

    private val paramValue1 = "paramValue1"

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlParam::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.param(
            paramName1,
            paramValue1,
        )
        val context = TestRenderContext()

        // when
        sut.serialize(part as JpqlParam<*>, writer, context)

        // then
        verifySequence {
            writer.writeParam(paramName1, paramValue1)
        }
    }
}

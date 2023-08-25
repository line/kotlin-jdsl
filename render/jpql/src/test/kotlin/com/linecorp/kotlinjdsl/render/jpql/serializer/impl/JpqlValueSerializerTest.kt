package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlValueSerializerTest : WithAssertions {
    private val sut = JpqlValueSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    private val value1 = "value1"

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlValue::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.value(
            value1,
        )
        val context = TestRenderContext()

        // when
        sut.serialize(part as JpqlValue<*>, writer, context)

        // then
        verifySequence {
            writer.writeParam(value1)
        }
    }
}

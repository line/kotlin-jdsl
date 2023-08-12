package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpression
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
class JpqlExpressionSerializerTest: WithAssertions {
    val sut = JpqlExpressionSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlExpression::class)
    }

    @Test
    fun `serialize - WHEN expression is given, THEN draw alias`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Expressions.expression(Long::class, "alias")
        val context = TestRenderContext()

        // when
        sut.serialize(part as JpqlExpression<*>, writer, context)

        // then
        verifySequence {
            writer.write(part.alias)
        }
    }
}



package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@JpqlSerializerTest
class JpqlSortSerializerTest : WithAssertions {
    private val sut = JpqlSortSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSort::class)
    }

    @ParameterizedTest
    @MethodSource("withoutNullOrder")
    fun `serialize - WHEN null order is not given, THEN draw only direction`(
        part: JpqlSort
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.expr, writer, context)

            writer.write(" ")
            writer.write(part.order.name)
        }
    }

    @ParameterizedTest
    @MethodSource("withNullOrder")
    fun `serialize - WHEN null order is given, THEN draw direction with null order`(
        part: JpqlSort
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.expr, writer, context)

            writer.write(" ")
            writer.write(part.order.name)
            writer.write(" ")

            writer.write("NULLS ${part.nullOrder!!.name}")
        }
    }

    companion object {
        @JvmStatic
        fun withoutNullOrder() = listOf(
            Arguments.of(Sorts.asc(Expressions.stringLiteral("expr"), null)),
            Arguments.of(Sorts.desc(Expressions.stringLiteral("expr"), null)),
        )

        @JvmStatic
        fun withNullOrder() = listOf(
            Arguments.of(Sorts.asc(Expressions.stringLiteral("expr"), Sort.NullOrder.FIRST)),
            Arguments.of(Sorts.desc(Expressions.stringLiteral("expr"), Sort.NullOrder.LAST)),
        )
    }
}

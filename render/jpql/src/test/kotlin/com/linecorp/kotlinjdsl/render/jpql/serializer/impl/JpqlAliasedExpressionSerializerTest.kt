package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAliasedExpression
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
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
class JpqlAliasedExpressionSerializerTest : WithAssertions {
    private val sut = JpqlAliasedExpressionSerializer()

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlAliasedExpression::class)
    }

    @Test
    fun `serialize - WHEN statement is select and clause is select, THEN draw full syntax`(
        @MockK writer: JpqlWriter,
        @MockK serializer: JpqlRenderSerializer,
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.alias(
            Expressions.stringLiteral("name"),
            Expressions.expression(String::class, "alias"),
        )

        val context = TestRenderContext(serializer, JpqlRenderStatement.Select, JpqlRenderClause.Select)

        // when
        sut.serialize(part as JpqlAliasedExpression<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.expr, writer, context)

            writer.write(" ")
            writer.write("AS")
            writer.write(" ")

            serializer.serialize(part.alias, writer, context)
        }
    }
}

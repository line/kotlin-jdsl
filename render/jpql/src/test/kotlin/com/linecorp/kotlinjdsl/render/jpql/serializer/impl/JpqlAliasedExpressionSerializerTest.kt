package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAliasedExpression
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.*
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest

@JpqlSerializerTest
class JpqlAliasedExpressionSerializerTest : WithAssertions {
    private val sut = JpqlAliasedExpressionSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlAliasedExpression::class)
    }

    @ParameterizedTest
    @StatementClauseSource(
        excludes = [
            StatementClause(statement = JpqlRenderStatement.Select::class, clause = JpqlRenderClause.Select::class)
        ],
    )
    fun `serialize - WHEN statement is not select and clause is not select, THEN draw only alias`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.alias(
            Expressions.stringLiteral("name"),
            Expressions.expression(String::class, "alias"),
        )

        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part as JpqlAliasedExpression<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.alias, writer, context)
        }
    }

    @Test
    fun `serialize - WHEN statement is select and clause is select, THEN draw full syntax`() {
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

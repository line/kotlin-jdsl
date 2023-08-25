package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAliasedExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.*
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
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

    private val expression1 = Paths.path(Book::title)
    private val expression2 = Expressions.expression(String::class, "t")

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlAliasedExpression::class)
    }

    @ParameterizedTest
    @StatementClauseSource(
        includes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.Select::class),
        ],
    )
    fun serialize(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        val part = Expressions.alias(
            expression1,
            expression2,
        )
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part as JpqlAliasedExpression<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            serializer.serialize(expression2, writer, context)
        }
    }

    @ParameterizedTest
    @StatementClauseSource(
        excludes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.Select::class),
        ],
    )
    fun `serialize() draws only the alias, when given the statement and clause of the source`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        val part = Expressions.alias(
            expression1,
            expression2,
        )
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part as JpqlAliasedExpression<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression2, writer, context)
        }
    }
}

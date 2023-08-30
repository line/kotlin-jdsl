package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
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
class JpqlDerivedEntitySerializerTest : WithAssertions {
    private val sut = JpqlDerivedEntitySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val selectQuery1 = SelectQueries.select(
        returnType = String::class,
        distinct = false,
        select = listOf(Paths.path(Book::title)),
        from = listOf(Entities.entity(Book::class)),
    )

    private val alias1 = "alias1"

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlDerivedEntity::class)
    }

    @ParameterizedTest
    @StatementClauseSource(
        includes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.From::class),
            StatementClause(JpqlRenderStatement.Update::class, JpqlRenderClause.Update::class),
            StatementClause(JpqlRenderStatement.Delete::class, JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun serialize(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        val part = Entities.derivedEntity(
            selectQuery = selectQuery1,
            alias = alias1,
        )
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.writeParentheses(any())
            serializer.serialize(selectQuery1, writer, context)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(alias1)
        }
    }

    @ParameterizedTest
    @StatementClauseSource(
        excludes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.From::class),
            StatementClause(JpqlRenderStatement.Update::class, JpqlRenderClause.Update::class),
            StatementClause(JpqlRenderStatement.Delete::class, JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun `serialize() draws only the alias, when given the statement and clause of the source`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        val part = Entities.derivedEntity(
            selectQuery = selectQuery1,
            alias = alias1,
        )
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.write(alias1)
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.*
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest

@JpqlSerializerTest
class JpqlDerivedEntitySerializerTest : WithAssertions {
    private val sut = JpqlDerivedEntitySerializer()
    data class TestEntity(val id: Long, val name: String)

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlDerivedEntity::class)
    }

    @ParameterizedTest
    @StatementClauseSource(
        excludes = [
            StatementClause(statement = JpqlRenderStatement.Select::class, clause = JpqlRenderClause.From::class),
            StatementClause(statement = JpqlRenderStatement.Update::class, clause = JpqlRenderClause.Update::class),
            StatementClause(statement = JpqlRenderStatement.Delete::class, clause = JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun `serialize - WHEN a combination of statement and clause is given as source, THEN draw only alias`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { writer.write(any<String>()) } just runs

        val select = Selects.select(
            returnType = TestEntity::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val part = Entities.derivedEntity(
            selectQuery = select,
            alias = "alias",
        )
        val context = TestRenderContext(serializer, JpqlRenderStatement.Select, JpqlRenderClause.DeleteFrom)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.write("alias")
        }
    }

    @ParameterizedTest
    @StatementClauseSource(
        includes = [
            StatementClause(statement = JpqlRenderStatement.Select::class, clause = JpqlRenderClause.From::class),
            StatementClause(statement = JpqlRenderStatement.Update::class, clause = JpqlRenderClause.Update::class),
            StatementClause(statement = JpqlRenderStatement.Delete::class, clause = JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun `serialize - WHEN a combination of statement and clause is given as source, THEN draw full syntax`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val select = Selects.select(
            returnType = TestEntity::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val part = Entities.derivedEntity(
            selectQuery = select,
            alias = "alias",
        )
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.write("(")
            serializer.serialize(part.selectQuery, writer, context)
            writer.write(")")

            writer.write(" ")
            writer.write("AS")
            writer.write(" ")

            writer.write(part.alias)
        }
    }
}

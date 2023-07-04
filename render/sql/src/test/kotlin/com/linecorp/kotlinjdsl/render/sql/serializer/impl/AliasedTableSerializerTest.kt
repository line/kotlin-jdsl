package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Table
import com.linecorp.kotlinjdsl.querymodel.sql.impl.AliasedTable
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderClause
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderStatement
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AliasedTableSerializerTest : WithAssertions {
    private val sut = AliasedTableSerializer()

    private val table1: Table<Any> = mockk()
    private val alias1 = "alias"

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(AliasedTable::class)
    }

    @Test
    fun `serialize - WHEN serialize outside of SELECT, THEN draw only the alias`() {
        // given
        val writer = mockkClass(SqlWriter::class) {
            every { writeKeyword(any()) } just runs
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(SqlRenderSerializer::class) {
            every { key } returns SqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val statement = mockkClass(SqlRenderStatement::class) {
            every { key } returns SqlRenderStatement
            every { isSelect() } returns false
        }

        val clause = mockkClass(SqlRenderClause::class) {
            every { key } returns SqlRenderClause
        }

        val part = AliasedTable(table1, alias1)
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part, writer, context)

        // then
        verify(exactly = 1) {
            statement.isSelect()
            writer.write(alias1)
        }

        verify {
            serializer.key
            statement.key
            clause.key
        }

        confirmVerified(
            writer,
            serializer,
            statement,
            clause,
        )
    }

    @Test
    fun `serialize - WHEN serialize in SELECT but outside of FROM, THEN draw only the alias`() {
        // given
        val writer = mockkClass(SqlWriter::class) {
            every { writeKeyword(any()) } just runs
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(SqlRenderSerializer::class) {
            every { key } returns SqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val statement = mockkClass(SqlRenderStatement::class) {
            every { key } returns SqlRenderStatement
            every { isSelect() } returns true
        }

        val clause = mockkClass(SqlRenderClause::class) {
            every { key } returns SqlRenderClause
            every { isFrom() } returns false
        }

        val part = AliasedTable(table1, alias1)
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part, writer, context)

        // then
        verify(exactly = 1) {
            statement.isSelect()
            clause.isFrom()
            writer.write(alias1)
        }

        verify {
            serializer.key
            statement.key
            clause.key
        }

        confirmVerified(
            writer,
            serializer,
            statement,
            clause,
        )
    }

    @Test
    fun `serialize - WHEN serialize in SELECT FROM, THEN draw the table first and draw the alias with the AS keyword`() {
        // given
        val writer = mockkClass(SqlWriter::class) {
            every { writeKeyword(any()) } just runs
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(SqlRenderSerializer::class) {
            every { key } returns SqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val statement = mockkClass(SqlRenderStatement::class) {
            every { key } returns SqlRenderStatement
            every { isSelect() } returns true
        }

        val clause = mockkClass(SqlRenderClause::class) {
            every { key } returns SqlRenderClause
            every { isFrom() } returns true
        }

        val part = AliasedTable(table1, alias1)
        val context = TestRenderContext(serializer, statement, clause)

        // when
        sut.serialize(part, writer, context)

        // then
        verify(exactly = 1) {
            serializer.serialize(table1, writer, context)
            statement.isSelect()
            clause.isFrom()
            writer.writeKeyword("AS")
            writer.write(alias1)
        }

        verify {
            serializer.key
            statement.key
            clause.key
        }

        confirmVerified(
            writer,
            serializer,
            statement,
            clause,
        )
    }
}

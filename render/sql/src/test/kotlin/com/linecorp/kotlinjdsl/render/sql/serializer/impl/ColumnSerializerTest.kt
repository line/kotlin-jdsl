package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Table
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlColumnDescription
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderClause
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderStatement
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KProperty1

@ExtendWith(MockKExtension::class)
class ColumnSerializerTest : WithAssertions {
    private val sut = ColumnSerializer()

    private val table1: Table<Any> = mockk()
    private val property1: KProperty1<Any, Any> = mockk()

    private val columnName1 = "columnName1"

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(com.linecorp.kotlinjdsl.querymodel.sql.Column::class)
    }

    @Test
    fun `serialize - WHEN serialize outside of INSERT, THEN draw the table first and draw the column name with dot`() {
        // given
        val writer = mockkClass(SqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(SqlRenderSerializer::class) {
            every { key } returns SqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val introspector = mockkClass(SqlRenderIntrospector::class) {
            every { key } returns SqlRenderIntrospector
            every { introspect(any<KProperty1<*, *>>()) } returns object : SqlColumnDescription {
                override val name: String = columnName1
            }
        }

        val statement = mockkClass(SqlRenderStatement::class) {
            every { key } returns SqlRenderStatement
            every { isInsert() } returns false
        }

        val clause = mockkClass(SqlRenderClause::class) {
            every { key } returns SqlRenderClause
        }

        val part = com.linecorp.kotlinjdsl.querymodel.sql.Column(table1, property1)
        val context = TestRenderContext(serializer, introspector, statement, clause)

        // when
        sut.serialize(part, writer, context)

        // then
        verify(exactly = 1) {
            statement.isInsert()
            serializer.serialize(table1, writer, context)
            introspector.introspect(property1)
            writer.write(".")
            writer.write(columnName1)
        }

        verify {
            serializer.key
            introspector.key
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
    fun `serialize - WHEN serialize in INSERT but outside of INTO or VALUES, THEN draw the table first and draw the column name with dot`() {
        // given
        val writer = mockkClass(SqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(SqlRenderSerializer::class) {
            every { key } returns SqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val introspector = mockkClass(SqlRenderIntrospector::class) {
            every { key } returns SqlRenderIntrospector
            every { introspect(any<KProperty1<*, *>>()) } returns object : SqlColumnDescription {
                override val name: String = columnName1
            }
        }

        val statement = mockkClass(SqlRenderStatement::class) {
            every { key } returns SqlRenderStatement
            every { isInsert() } returns true
        }

        val clause = mockkClass(SqlRenderClause::class) {
            every { key } returns SqlRenderClause
            every { isInto() } returns false
            every { isValues() } returns false
        }

        val part = com.linecorp.kotlinjdsl.querymodel.sql.Column(table1, property1)
        val context = TestRenderContext(serializer, introspector, statement, clause)

        // when
        sut.serialize(part, writer, context)

        // then
        verify(exactly = 1) {
            statement.isInsert()
            clause.isInto()
            clause.isValues()
            serializer.serialize(table1, writer, context)
            introspector.introspect(property1)
            writer.write(".")
            writer.write(columnName1)
        }

        verify {
            serializer.key
            introspector.key
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
    fun `serialize - WHEN serialize in INSERT INTO, THEN draw only column name`() {
        // given
        val writer = mockkClass(SqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(SqlRenderSerializer::class) {
            every { key } returns SqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val introspector = mockkClass(SqlRenderIntrospector::class) {
            every { key } returns SqlRenderIntrospector
            every { introspect(any<KProperty1<*, *>>()) } returns object : SqlColumnDescription {
                override val name: String = columnName1
            }
        }

        val statement = mockkClass(SqlRenderStatement::class) {
            every { key } returns SqlRenderStatement
            every { isInsert() } returns true
        }

        val clause = mockkClass(SqlRenderClause::class) {
            every { key } returns SqlRenderClause
            every { isInto() } returns true
        }

        val part = com.linecorp.kotlinjdsl.querymodel.sql.Column(table1, property1)
        val context = TestRenderContext(serializer, introspector, statement, clause)

        // when
        sut.serialize(part, writer, context)

        // then
        verify(exactly = 1) {
            statement.isInsert()
            clause.isInto()
            introspector.introspect(property1)
            writer.write(columnName1)
        }

        verify {
            serializer.key
            introspector.key
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
    fun `serialize - WHEN serialize in INSERT VALUES, THEN draw only column name`() {
        // given
        val writer = mockkClass(SqlWriter::class) {
            every { write(any<String>()) } just runs
        }

        val serializer = mockkClass(SqlRenderSerializer::class) {
            every { key } returns SqlRenderSerializer
            every { serialize(any(), any(), any()) } just runs
        }

        val introspector = mockkClass(SqlRenderIntrospector::class) {
            every { key } returns SqlRenderIntrospector
            every { introspect(any<KProperty1<*, *>>()) } returns object : SqlColumnDescription {
                override val name: String = columnName1
            }
        }

        val statement = mockkClass(SqlRenderStatement::class) {
            every { key } returns SqlRenderStatement
            every { isInsert() } returns true
        }

        val clause = mockkClass(SqlRenderClause::class) {
            every { key } returns SqlRenderClause
            every { isInto() } returns false
            every { isValues() } returns true
        }

        val part = com.linecorp.kotlinjdsl.querymodel.sql.Column(table1, property1)
        val context = TestRenderContext(serializer, introspector, statement, clause)

        // when
        sut.serialize(part, writer, context)

        // then
        verify(exactly = 1) {
            statement.isInsert()
            clause.isInto()
            clause.isValues()
            introspector.introspect(property1)
            writer.write(columnName1)
        }

        verify {
            serializer.key
            introspector.key
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

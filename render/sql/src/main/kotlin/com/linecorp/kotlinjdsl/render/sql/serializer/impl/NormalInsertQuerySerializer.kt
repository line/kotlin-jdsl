package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Table
import com.linecorp.kotlinjdsl.querymodel.sql.impl.NormalInsertQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderClause
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderStatement
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class NormalInsertQuerySerializer : SqlSerializer<NormalInsertQuery<*>> {
    override fun handledType(): KClass<NormalInsertQuery<*>> {
        return NormalInsertQuery::class
    }

    override fun serialize(part: NormalInsertQuery<*>, writer: SqlWriter, context: RenderContext) {
        val into = part.table
        val columns = part.columns
        val values = part.values
        val select = part.select

        val delegate = context.getValue(SqlRenderSerializer)
        val insertContext = context + SqlRenderStatement.Insert

        val insertIntoContext = insertContext + SqlRenderClause.Into

        writer.writeKeyword("INSERT")
        writer.writeKeyword("INTO")
        into(into, delegate, writer, insertIntoContext)

        if (columns != null) {
            columns(columns, delegate, writer, insertIntoContext)
        }

        if (values != null) {
            val insertValuesContext = insertContext + SqlRenderClause.Values

            writer.writeKeyword("VALUES")
            values(values, delegate, writer, insertValuesContext)
        }

        if (select != null) {
            select(select, delegate, writer, insertContext)
        }
    }

    private fun into(
        table: Table<*>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        serializer.serialize(table, writer, context)
    }

    private fun columns(
        columns: Iterable<Expression<*>>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        writer.write("(")

        writer.writeEach(columns, separator = ", ") { column ->
            serializer.serialize(column, writer, context)
        }

        writer.write(")")
    }

    private fun values(
        values: Iterable<Iterable<Expression<*>>>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        writer.writeEach(values, separator = ", ") { elements ->
            writer.write("(")

            writer.writeEach(elements, separator = ", ") { element ->
                serializer.serialize(element, writer, context)
            }

            writer.write(")")
        }
    }

    private fun select(
        table: Table<*>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        serializer.serialize(table, writer, context)
    }
}

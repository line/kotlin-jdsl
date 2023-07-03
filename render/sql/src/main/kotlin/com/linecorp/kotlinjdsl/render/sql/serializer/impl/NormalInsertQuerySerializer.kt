package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Table
import com.linecorp.kotlinjdsl.query.sql.impl.NormalInsertQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.generator.SqlWriter
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderClause
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderStatement
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
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
            val insertSelectContext = insertContext + SqlRenderClause.Select

            select(select, delegate, writer, insertSelectContext)
        }
    }

    private fun into(
        table: Table<*>,
        serializer: SqlRenderSerializer,
        generator: SqlWriter,
        context: RenderContext,
    ) {
        serializer.serialize(table, generator, context)
    }

    private fun columns(
        columns: Collection<Expression<*>>,
        serializer: SqlRenderSerializer,
        generator: SqlWriter,
        context: RenderContext,
    ) {
        generator.write("(")

        generator.writeEach(columns, separator = ", ") { column ->
            serializer.serialize(column, generator, context)
        }

        generator.write(")")
    }

    private fun values(
        values: Collection<Collection<Expression<*>>>,
        serializer: SqlRenderSerializer,
        generator: SqlWriter,
        context: RenderContext,
    ) {
        generator.writeEach(values, separator = ", ") { elements ->
            generator.write("(")

            generator.writeEach(elements, separator = ", ") { element ->
                serializer.serialize(element, generator, context)
            }

            generator.write(")")
        }
    }

    private fun select(
        table: Table<*>,
        serializer: SqlRenderSerializer,
        generator: SqlWriter,
        context: RenderContext,
    ) {
        serializer.serialize(table, generator, context)
    }
}

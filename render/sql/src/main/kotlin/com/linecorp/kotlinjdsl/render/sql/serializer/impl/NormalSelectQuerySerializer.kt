package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.Sort
import com.linecorp.kotlinjdsl.querymodel.sql.Table
import com.linecorp.kotlinjdsl.querymodel.sql.impl.NormalSelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderClause
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderStatement
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class NormalSelectQuerySerializer : SqlSerializer<NormalSelectQuery> {
    override fun handledType(): KClass<NormalSelectQuery> {
        return NormalSelectQuery::class
    }

    override fun serialize(part: NormalSelectQuery, writer: SqlWriter, context: RenderContext) {
        val select = part.select
        val from = part.from
        val where = part.where
        val groupBy = part.groupBy
        val having = part.having
        val orderBy = part.orderBy
        val offset = part.offset
        val limit = part.limit

        val delegate = context.getValue(SqlRenderSerializer)
        val selectContext = context + SqlRenderStatement.Select

        val selectSelectContext = selectContext + SqlRenderClause.Select

        writer.writeKeyword("SELECT")
        if (part.distinct) {
            writer.writeKeyword("DISTINCT")
        }
        select(select, delegate, writer, selectSelectContext)

        if (from != null) {
            val selectFromContext = selectContext + SqlRenderClause.From

            writer.writeKeyword("FROM")
            from(from, delegate, writer, selectFromContext)
        }

        if (where != null) {
            val selectWhereContext = selectContext + SqlRenderClause.Where

            writer.writeKeyword("WHERE")
            where(where, delegate, writer, selectWhereContext)
        }

        if (groupBy != null) {
            val selectGroupByContext = selectContext + SqlRenderClause.GroupBy

            writer.writeKeyword("GROUP")
            writer.writeKeyword("BY")
            groupBy(groupBy, delegate, writer, selectGroupByContext)
        }

        if (having != null) {
            val selectHavingContext = selectContext + SqlRenderClause.Having

            writer.writeKeyword("HAVING")
            having(having, delegate, writer, selectHavingContext)
        }

        if (orderBy != null) {
            val selectOrderByContext = selectContext + SqlRenderClause.OrderBy

            writer.writeKeyword("ORDER")
            writer.writeKeyword("BY")
            orderBy(orderBy, delegate, writer, selectOrderByContext)
        }

        if (offset != null) {
            writer.writeKeyword("OFFSET")
            writer.write(offset)
        }

        if (limit != null) {
            writer.writeKeyword("LIMIT")
            writer.write(limit)
        }
    }

    private fun select(
        select: Iterable<Expression<*>>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        writer.writeEach(select, separator = ", ") {
            serializer.serialize(it, writer, context)
        }
    }

    private fun from(
        from: Iterable<Table<*>>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        writer.writeEach(from, separator = ", ") {
            serializer.serialize(it, writer, context)
        }
    }

    private fun where(
        where: Predicate,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        serializer.serialize(where, writer, context)
    }

    private fun groupBy(
        groupBy: Iterable<Expression<*>>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        writer.writeEach(groupBy, separator = ", ") {
            serializer.serialize(it, writer, context)
        }
    }

    private fun having(
        having: Predicate,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        serializer.serialize(having, writer, context)
    }

    private fun orderBy(
        orderBy: Iterable<Sort>,
        serializer: SqlRenderSerializer,
        writer: SqlWriter,
        context: RenderContext,
    ) {
        writer.writeEach(orderBy, separator = ", ") {
            serializer.serialize(it, writer, context)
        }
    }
}


package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlSelectQuerySerializer : JpqlSerializer<JpqlSelectQuery<*>> {
    override fun handledType(): KClass<JpqlSelectQuery<*>> {
        return JpqlSelectQuery::class
    }

    override fun serialize(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val selectContext = context + JpqlRenderStatement.Select

        writeSelect(part, writer, selectContext)
        writeFrom(part, writer, selectContext)
        writeWhere(part, writer, selectContext)
        writeGroupBy(part, writer, selectContext)
        writeHaving(part, writer, selectContext)
        writeOrderBy(part, writer, selectContext)
    }

    private fun writeSelect(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val select = part.select
        val distinct = part.distinct
        val delegate = context.getValue(JpqlRenderSerializer)

        val selectContext = context + JpqlRenderClause.Select

        writer.write("SELECT")
        writer.write(" ")

        if (distinct) {
            writer.write("DISTINCT")
            writer.write(" ")
        }

        writer.writeEach(select, separator = ", ") {
            delegate.serialize(it, writer, selectContext)
        }
    }

    private fun writeFrom(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val from = part.from
        val delegate = context.getValue(JpqlRenderSerializer)

        val fromContext = context + JpqlRenderClause.From

        writer.write(" ")
        writer.write("FROM")
        writer.write(" ")

        writer.writeEach(from, separator = ", ") {
            delegate.serialize(it, writer, fromContext)
        }
    }

    private fun writeWhere(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val where = part.where ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val whereContext = context + JpqlRenderClause.Where

        writer.write(" ")
        writer.write("WHERE")
        writer.write(" ")

        delegate.serialize(where, writer, whereContext)
    }

    private fun writeGroupBy(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val groupBy = part.groupBy ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val groupByContext = context + JpqlRenderClause.GroupBy

        writer.write(" ")
        writer.write("GROUP BY")
        writer.write(" ")

        writer.writeEach(groupBy, separator = ", ") {
            delegate.serialize(it, writer, groupByContext)
        }
    }

    private fun writeHaving(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val having = part.having ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val havingContext = context + JpqlRenderClause.Having

        writer.write(" ")
        writer.write("HAVING")
        writer.write(" ")

        delegate.serialize(having, writer, havingContext)
    }

    private fun writeOrderBy(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val orderBy = part.orderBy ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val orderByContext = context + JpqlRenderClause.OrderBy

        writer.write(" ")
        writer.write("ORDER BY")
        writer.write(" ")

        writer.writeEach(orderBy, separator = ", ") {
            delegate.serialize(it, writer, orderByContext)
        }
    }
}

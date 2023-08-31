package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.iterable.IterableUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlSelectQuerySerializer : JpqlSerializer<JpqlSelectQuery<*>> {
    override fun handledType(): KClass<JpqlSelectQuery<*>> {
        return JpqlSelectQuery::class
    }

    override fun serialize(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val newContext = context + JpqlRenderStatement.Select

        writeSelect(part, writer, newContext)
        writeFrom(part, writer, newContext)
        writeWhere(part, writer, newContext)
        writeGroupBy(part, writer, newContext)
        writeHaving(part, writer, newContext)
        writeOrderBy(part, writer, newContext)
    }

    private fun writeSelect(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val select = part.select
        val distinct = part.distinct
        val delegate = context.getValue(JpqlRenderSerializer)

        val newContext = context + JpqlRenderClause.Select

        writer.write("SELECT")
        writer.write(" ")

        if (distinct) {
            writer.write("DISTINCT")
            writer.write(" ")
        }

        writer.writeEach(select, separator = ", ") {
            delegate.serialize(it, writer, newContext)
        }
    }

    private fun writeFrom(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val from = part.from
        val delegate = context.getValue(JpqlRenderSerializer)

        val newContext = context + JpqlRenderClause.From

        writer.write(" ")
        writer.write("FROM")
        writer.write(" ")

        writer.writeEach(from, separator = ", ") {
            delegate.serialize(it, writer, newContext)
        }
    }

    private fun writeWhere(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val where = part.where ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val newContext = context + JpqlRenderClause.Where

        writer.write(" ")
        writer.write("WHERE")
        writer.write(" ")

        delegate.serialize(where, writer, newContext)
    }

    private fun writeGroupBy(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val groupBy = part.groupBy?.takeIf { IterableUtils.isNotEmpty(it) } ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val newContext = context + JpqlRenderClause.GroupBy

        writer.write(" ")
        writer.write("GROUP BY")
        writer.write(" ")

        writer.writeEach(groupBy, separator = ", ") {
            delegate.serialize(it, writer, newContext)
        }
    }

    private fun writeHaving(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val having = part.having ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val newContext = context + JpqlRenderClause.Having

        writer.write(" ")
        writer.write("HAVING")
        writer.write(" ")

        delegate.serialize(having, writer, newContext)
    }

    private fun writeOrderBy(part: JpqlSelectQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val orderBy = part.orderBy?.takeIf { IterableUtils.isNotEmpty(it) } ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val newContext = context + JpqlRenderClause.OrderBy

        writer.write(" ")
        writer.write("ORDER BY")
        writer.write(" ")

        writer.writeEach(orderBy, separator = ", ") {
            delegate.serialize(it, writer, newContext)
        }
    }
}

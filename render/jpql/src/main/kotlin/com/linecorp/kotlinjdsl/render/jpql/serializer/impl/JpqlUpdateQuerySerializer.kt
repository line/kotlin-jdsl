package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.update.impl.JpqlUpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlUpdateQuerySerializer : JpqlSerializer<JpqlUpdateQuery<*>> {
    override fun handledType(): KClass<JpqlUpdateQuery<*>> {
        return JpqlUpdateQuery::class
    }

    override fun serialize(part: JpqlUpdateQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val updateContext = context + JpqlRenderStatement.Update

        writeUpdate(part, writer, updateContext)
        writeSet(part, writer, updateContext)
        writeWhere(part, writer, updateContext)
    }

    private fun writeUpdate(part: JpqlUpdateQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val entity = part.entity
        val delegate = context.getValue(JpqlRenderSerializer)

        val updateContext = context + JpqlRenderClause.Update

        writer.write("UPDATE")
        writer.write(" ")

        delegate.serialize(entity, writer, updateContext)
    }

    private fun writeSet(part: JpqlUpdateQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val set = part.set
        val delegate = context.getValue(JpqlRenderSerializer)

        val fromContext = context + JpqlRenderClause.Set

        writer.write(" ")
        writer.write("SET")
        writer.write(" ")

        writer.writeEach(set.entries, separator = ", ") { (path, expr) ->
            delegate.serialize(path, writer, fromContext)

            writer.write(" ")
            writer.write("=")
            writer.write(" ")

            delegate.serialize(expr, writer, fromContext)
        }
    }

    private fun writeWhere(part: JpqlUpdateQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val where = part.where ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val whereContext = context + JpqlRenderClause.Where

        writer.write(" ")
        writer.write("WHERE")
        writer.write(" ")

        delegate.serialize(where, writer, whereContext)
    }
}

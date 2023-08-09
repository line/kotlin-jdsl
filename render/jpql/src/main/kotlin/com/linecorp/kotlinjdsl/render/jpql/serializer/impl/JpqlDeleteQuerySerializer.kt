package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlDeleteQuerySerializer : JpqlSerializer<JpqlDeleteQuery<*>> {
    override fun handledType(): KClass<JpqlDeleteQuery<*>> {
        return JpqlDeleteQuery::class
    }

    override fun serialize(part: JpqlDeleteQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val updateContext = context + JpqlRenderStatement.Delete

        writeDelete(part, writer, updateContext)
        writeWhere(part, writer, updateContext)
    }

    private fun writeDelete(part: JpqlDeleteQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val entity = part.entity
        val delegate = context.getValue(JpqlRenderSerializer)

        val selectContext = context + JpqlRenderClause.DeleteFrom

        writer.write("DELETE")
        writer.write(" ")

        delegate.serialize(entity, writer, selectContext)
    }

    private fun writeWhere(part: JpqlDeleteQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val where = part.where ?: return
        val delegate = context.getValue(JpqlRenderSerializer)

        val whereContext = context + JpqlRenderClause.Where

        writer.write(" ")
        writer.write("WHERE")
        writer.write(" ")

        delegate.serialize(where, writer, whereContext)
    }
}

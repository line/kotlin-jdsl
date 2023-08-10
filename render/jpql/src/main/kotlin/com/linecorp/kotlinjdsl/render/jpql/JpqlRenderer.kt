package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.impl.DefaultJpqlWriter

class JpqlRenderer {
    fun render(query: JpqlQuery<*>, context: RenderContext): JpqlRendered {
        return render(query, emptyMap(), context)
    }

    fun render(query: JpqlQuery<*>, params: Map<String, Any?>, context: RenderContext): JpqlRendered {
        val serializer = context.getValue(JpqlRenderSerializer)
        val writer = createWriter(params)

        serializer.serialize(query, writer, context)

        return JpqlRendered(
            query = writer.getQuery(),
            params = writer.getParams(),
        )
    }

    private fun createWriter(params: Map<String, Any?>): DefaultJpqlWriter {
        return DefaultJpqlWriter(params)
    }
}

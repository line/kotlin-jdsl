package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.impl.DefaultJpqlWriter
import org.slf4j.LoggerFactory

@SinceJdsl("3.0.0")
class JpqlRenderer {
    @SinceJdsl("3.0.0")
    fun render(query: JpqlQuery<*>, context: RenderContext): JpqlRendered {
        return render(query, emptyMap(), context)
    }

    @SinceJdsl("3.0.0")
    fun render(query: JpqlQuery<*>, params: Map<String, Any?>, context: RenderContext): JpqlRendered {
        val serializer = context.getValue(JpqlRenderSerializer)
        val writer = createWriter(params)

        serializer.serialize(query, writer, context)

        val renderedQuery = writer.getQuery()
        val renderedParams = writer.getParams()

        if (log.isDebugEnabled) {
            log.debug("The query is rendered.\n{}\n{}", renderedQuery, renderedParams.toMap())
        }

        return JpqlRendered(
            query = renderedQuery,
            params = renderedParams,
        )
    }

    private fun createWriter(params: Map<String, Any?>): DefaultJpqlWriter {
        return DefaultJpqlWriter(params)
    }
}

private val log = LoggerFactory.getLogger(JpqlRenderer::class.java)

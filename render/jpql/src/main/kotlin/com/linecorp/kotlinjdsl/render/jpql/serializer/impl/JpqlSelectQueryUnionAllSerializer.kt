package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnionAll
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlSelectQueryUnionAllSerializer : JpqlSerializer<JpqlSelectQueryUnionAll<*>> {
    override fun handledType(): KClass<JpqlSelectQueryUnionAll<*>> =
        JpqlSelectQueryUnionAll::class

    override fun serialize(part: JpqlSelectQueryUnionAll<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val queryContext = context + JpqlRenderStatement.Select

        delegate.serialize(part.left.toQuery(), writer, queryContext)

        writer.write(" UNION ALL ")

        delegate.serialize(part.right.toQuery(), writer, queryContext)

        writeOrderBy(part, queryContext, writer, delegate)
    }

    private fun writeOrderBy(
        part: JpqlSelectQueryUnionAll<*>,
        queryContext: RenderContext,
        writer: JpqlWriter,
        delegate: JpqlRenderSerializer,
    ) {
        part.orderBy?.takeIf { it.any() }?.let { orderByItems ->
            val orderByInSetOperationContext = queryContext + JpqlRenderClause.OrderBy
            writer.write(" ")
            writer.write("ORDER BY")
            writer.write(" ")
            writer.writeEach(orderByItems, separator = ", ") { sortElement ->
                delegate.serialize(sortElement, writer, orderByInSetOperationContext)
            }
        }
    }
}

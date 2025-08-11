package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryIntersect
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter

@Internal
@SinceJdsl("3.6.0")
class JpqlSelectQueryIntersectSerializer : JpqlSerializer<JpqlSelectQueryIntersect<*>> {
    override fun handledType() = JpqlSelectQueryIntersect::class
    override fun serialize(
        part: JpqlSelectQueryIntersect<*>,
        writer: JpqlWriter,
        context: RenderContext,
    ) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val queryContext = context + JpqlRenderStatement.Select

        delegate.serialize(part.left.toQuery(), writer, queryContext)

        writer.write(" INTERSECT ")

        delegate.serialize(part.right.toQuery(), writer, queryContext)

        writeOrderBy(part, queryContext, writer, delegate)
    }

    private fun writeOrderBy(
        part: JpqlSelectQueryIntersect<*>,
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

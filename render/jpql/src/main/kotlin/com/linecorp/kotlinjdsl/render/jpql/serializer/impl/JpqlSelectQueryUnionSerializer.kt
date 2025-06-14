package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnion
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlSelectQueryUnionSerializer : JpqlSerializer<JpqlSelectQueryUnion<*>> {
    override fun handledType(): KClass<JpqlSelectQueryUnion<*>> =
        JpqlSelectQueryUnion::class

    override fun serialize(part: JpqlSelectQueryUnion<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val queryContext = context + JpqlRenderStatement.Select // Indicate we are in a Set Operation context

        writer.writeParentheses {
            delegate.serialize(part.left.toQuery(), writer, queryContext)

            writer.write(" UNION ")

            delegate.serialize(part.right.toQuery(), writer, queryContext)
        }

        // Render ORDER BY clause if present
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

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.set.impl.JpqlSetOperationQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlSetOperationQuerySerializer : JpqlSerializer<JpqlSetOperationQuery<*>> {
    override fun handledType(): KClass<JpqlSetOperationQuery<*>> =
        JpqlSetOperationQuery::class

    override fun serialize(part: JpqlSetOperationQuery<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val queryContext = context + JpqlRenderStatement.Select // Indicate we are in a Set Operation context

        writer.writeParentheses {
            delegate.serialize(part.leftQuery, writer, queryContext)

            writer.write(" ")
            writer.write(part.operationType.value)
            writer.write(" ")

            delegate.serialize(part.rightQuery, writer, queryContext)
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

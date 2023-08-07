package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlAliasedExpression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlAliasedExpressionSerializer : JpqlSerializer<JpqlAliasedExpression<*>> {
    override fun handledType(): KClass<JpqlAliasedExpression<*>> {
        return JpqlAliasedExpression::class
    }

    override fun serialize(part: JpqlAliasedExpression<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val statement = context.getValue(JpqlRenderStatement)
        val clause = context.getValue(JpqlRenderClause)

        if ((statement.isSelect() && clause.isSelect())) {
            delegate.serialize(part.expr, writer, context)

            writer.writeIfAbsent(" ")
            writer.write("AS")
            writer.write(" ")
        }

        delegate.serialize(part.alias, writer, context)
    }
}

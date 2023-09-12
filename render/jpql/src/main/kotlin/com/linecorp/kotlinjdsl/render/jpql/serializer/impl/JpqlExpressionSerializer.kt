package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlExpression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlExpressionSerializer : JpqlSerializer<JpqlExpression<*>> {
    override fun handledType(): KClass<JpqlExpression<*>> {
        return JpqlExpression::class
    }

    override fun serialize(part: JpqlExpression<*>, writer: JpqlWriter, context: RenderContext) {
        writer.write(part.alias)
    }
}

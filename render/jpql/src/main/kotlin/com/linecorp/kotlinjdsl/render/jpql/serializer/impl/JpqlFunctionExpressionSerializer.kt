package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlFunctionExpression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.support.JpqlFunctionSerializerSupport
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlFunctionExpressionSerializer : JpqlFunctionSerializerSupport(), JpqlSerializer<JpqlFunctionExpression<*>> {
    override fun handledType(): KClass<JpqlFunctionExpression<*>> {
        return JpqlFunctionExpression::class
    }

    override fun serialize(part: JpqlFunctionExpression<*>, writer: JpqlWriter, context: RenderContext) {
        serialize(part.name, part.args, writer, context)
    }
}

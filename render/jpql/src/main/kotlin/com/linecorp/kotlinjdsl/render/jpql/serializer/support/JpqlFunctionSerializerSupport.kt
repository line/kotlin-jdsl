package com.linecorp.kotlinjdsl.render.jpql.serializer.support

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.iterable.IterableUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter

@Internal
open class JpqlFunctionSerializerSupport {
    protected fun serialize(name: String, args: Iterable<Expression<*>>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        writer.write("FUNCTION")

        writer.writeParentheses {
            writer.write("'")
            writer.write(name)
            writer.write("'")

            if (IterableUtils.isNotEmpty(args)) {
                writer.write(",")
                writer.write(" ")

                writer.writeEach(args, separator = ", ") {
                    delegate.serialize(it, writer, context)
                }
            }
        }
    }
}

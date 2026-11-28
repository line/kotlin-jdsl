package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.iterable.IterableUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIn
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.ExpressionUtils
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlInSerializer : JpqlSerializer<JpqlIn<*>> {
    override fun handledType(): KClass<JpqlIn<*>> = JpqlIn::class

    override fun serialize(
        part: JpqlIn<*>,
        writer: JpqlWriter,
        context: RenderContext,
    ) {
        val delegate = context.getValue(JpqlRenderSerializer)

        if (IterableUtils.isEmpty(part.compareValues)) {
            writer.write("0 = 1")
        } else {
            delegate.serialize(part.value, writer, context)

            writer.write(" ")
            writer.write("IN")
            writer.write(" ")

            val compareValues = part.compareValues.toList()
            if (compareValues.all { ExpressionUtils.isBasicType(it) }) {
                val values = compareValues.map { (it as JpqlValue<*>).value }
                val singleParam = Expressions.value(values)
                writer.writeParentheses {
                    delegate.serialize(singleParam, writer, context)
                }
            } else {
                writer.writeParentheses {
                    writer.writeEach(compareValues, separator = ", ") {
                        delegate.serialize(it, writer, context)
                    }
                }
            }
        }
    }
}

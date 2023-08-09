package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.iterable.IterableUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlOr
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlOrSerializer : JpqlSerializer<JpqlOr> {
    override fun handledType(): KClass<JpqlOr> {
        return JpqlOr::class
    }

    override fun serialize(part: JpqlOr, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        if (IterableUtils.isEmpty(part.predicates)) {
            writer.write("0 = 1")
        } else {
            writer.writeEach(part.predicates, separator = " OR ") {
                delegate.serialize(it, writer, context)
            }
        }
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlSortSerializer : JpqlSerializer<JpqlSort> {
    override fun handledType(): KClass<JpqlSort> {
        return JpqlSort::class
    }

    override fun serialize(part: JpqlSort, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        delegate.serialize(part.expr, writer, context)

        writer.writeIfAbsent(" ")

        when (part.order) {
            Sort.Order.ASC -> writer.write("ASC")
            Sort.Order.DESC -> writer.write("DESC")
        }

        val nullOrder = part.nullOrder

        if (nullOrder != null) {
            writer.writeIfAbsent(" ")

            when (nullOrder) {
                Sort.NullOrder.FIRST -> writer.write("NULLS FIRST")
                Sort.NullOrder.LAST -> writer.write("NULLS LAST")
            }
        }
    }
}

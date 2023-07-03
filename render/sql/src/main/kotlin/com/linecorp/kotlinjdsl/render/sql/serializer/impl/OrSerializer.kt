package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.query.sql.impl.Or
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class OrSerializer : SqlSerializer<Or> {
    override fun handledType(): KClass<Or> {
        return Or::class
    }

    override fun serialize(part: Or, writer: SqlWriter, context: RenderContext) {
        if (part.predicates.isEmpty()) return

        val delegate = context.getValue(SqlRenderSerializer)

        writer.writeEach(part.predicates, separator = { writer.writeKeyword("OR") }) { predicate ->
            delegate.serialize(predicate, writer, context)
        }
    }
}

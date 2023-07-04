package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.impl.And
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class AndSerializer : SqlSerializer<And> {
    override fun handledType(): KClass<And> {
        return And::class
    }

    override fun serialize(part: And, writer: SqlWriter, context: RenderContext) {
        if (part.predicates.isEmpty()) return

        val delegate = context.getValue(SqlRenderSerializer)

        writer.writeEach(part.predicates, separator = { writer.writeKeyword("AND") }) { predicate ->
            delegate.serialize(predicate, writer, context)
        }
    }
}

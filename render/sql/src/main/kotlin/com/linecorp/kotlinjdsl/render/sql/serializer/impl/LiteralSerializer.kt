package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.impl.Literal
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class LiteralSerializer : SqlSerializer<Literal<*>> {
    override fun handledType(): KClass<Literal<*>> {
        return Literal::class
    }

    override fun serialize(part: Literal<*>, writer: SqlWriter, context: RenderContext) {
        writer.writeValue(part.value)
    }
}

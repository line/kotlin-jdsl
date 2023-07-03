package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.query.sql.impl.Literal
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.generator.SqlWriter
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import kotlin.reflect.KClass

class LiteralSerializer : SqlSerializer<Literal<*>> {
    override fun handledType(): KClass<Literal<*>> {
        return Literal::class
    }

    override fun serialize(part: Literal<*>, writer: SqlWriter, context: RenderContext) {
        writer.writeValue(part.value)
    }
}

package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.impl.Param
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class ParamSerializer : SqlSerializer<Param<*>> {
    override fun handledType(): KClass<Param<*>> {
        return Param::class
    }

    override fun serialize(part: Param<*>, writer: SqlWriter, context: RenderContext) {
        writer.writeParam(part.value)
    }
}

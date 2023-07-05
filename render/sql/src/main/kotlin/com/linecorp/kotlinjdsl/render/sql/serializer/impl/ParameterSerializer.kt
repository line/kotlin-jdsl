package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.impl.Parameter
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class ParameterSerializer : SqlSerializer<Parameter<*>> {
    override fun handledType(): KClass<Parameter<*>> {
        return Parameter::class
    }

    override fun serialize(part: Parameter<*>, writer: SqlWriter, context: RenderContext) {
        writer.writeParameter(part.value)
    }
}

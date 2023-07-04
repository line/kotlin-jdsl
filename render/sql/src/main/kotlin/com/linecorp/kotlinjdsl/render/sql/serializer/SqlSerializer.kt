package com.linecorp.kotlinjdsl.render.sql.serializer

import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

interface SqlSerializer<T : QueryPart> {
    fun handledType(): KClass<T>

    fun serialize(part: T, writer: SqlWriter, context: RenderContext)
}

package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.query.sql.impl.TableAsterisk
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.generator.SqlWriter
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import kotlin.reflect.KClass

class TableAsteriskSerializer : SqlSerializer<TableAsterisk<*>> {
    override fun handledType(): KClass<TableAsterisk<*>> {
        return TableAsterisk::class
    }

    override fun serialize(part: TableAsterisk<*>, writer: SqlWriter, context: RenderContext) {
        val delegate = context.getValue(SqlRenderSerializer)

        delegate.serialize(part.table, writer, context)
        writer.write(".*")
    }
}

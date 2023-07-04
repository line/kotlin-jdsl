package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.impl.TableAsterisk
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
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

package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.query.sql.TableReference
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class TableReferenceSerializer : SqlSerializer<TableReference<*>> {
    override fun handledType(): KClass<TableReference<*>> {
        return TableReference::class
    }

    override fun serialize(part: TableReference<*>, writer: SqlWriter, context: RenderContext) {
        val table = context.getValue(SqlRenderIntrospector).introspect(part.type)

        writer.write(table.name)
    }
}

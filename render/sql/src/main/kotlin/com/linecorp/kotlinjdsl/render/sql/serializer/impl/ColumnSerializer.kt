package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Column
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderClause
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderStatement
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class ColumnSerializer : SqlSerializer<Column<*, *>> {
    override fun handledType(): KClass<Column<*, *>> {
        return Column::class
    }

    override fun serialize(part: Column<*, *>, writer: SqlWriter, context: RenderContext) {
        val statement = context.getValue(SqlRenderStatement)
        val clause = context.getValue(SqlRenderClause)
        val column = context.getValue(SqlRenderIntrospector).introspect(part.property)

        if (statement.isInsert() && (clause.isInto() || clause.isValues())) {
            writer.write(column.name)
        } else {
            val delegate = context.getValue(SqlRenderSerializer)

            delegate.serialize(part.table, writer, context)

            writer.write(".")
            writer.write(column.name)
        }
    }
}

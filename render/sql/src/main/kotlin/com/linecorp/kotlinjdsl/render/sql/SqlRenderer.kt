package com.linecorp.kotlinjdsl.render.sql

import com.linecorp.kotlinjdsl.querymodel.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.setting.SqlRenderSetting
import com.linecorp.kotlinjdsl.render.sql.writer.impl.DefaultSqlWriter

class SqlRenderer(
    private val setting: SqlRenderSetting,
) {
    fun render(query: SqlQuery<*>, context: RenderContext): SqlRendered {
        val serializer = context.getValue(SqlRenderSerializer)
        val writer = createWriter(setting)

        serializer.serialize(query, writer, context)

        return SqlRendered(
            query = writer.getQuery(),
            parameters = writer.getParameters(),
        )
    }

    private fun createWriter(setting: SqlRenderSetting): DefaultSqlWriter {
        return DefaultSqlWriter(
            paramType = setting.paramType,
        )
    }
}

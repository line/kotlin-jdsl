package com.linecorp.kotlinjdsl.executor.jdbc

import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParams
import com.linecorp.kotlinjdsl.render.sql.SqlRenderer
import com.linecorp.kotlinjdsl.render.sql.setting.ParamType
import com.linecorp.kotlinjdsl.render.sql.setting.SqlRenderSetting
import java.sql.Connection
import java.sql.PreparedStatement

private val sqlRenderer = SqlRenderer(
    setting = SqlRenderSetting(
        paramType = ParamType.INDEXED,
    ),
)

fun Connection.prepareStatement(
    query: SqlQuery<SelectQuery>,
    context: RenderContext,
): PreparedStatement {
    val rendered = sqlRenderer.render(query, context)

    val renderedQuery = rendered.query
    val renderedParams = rendered.params as SqlRenderedParams.Indexed

    val preparedStatement = prepareStatement(renderedQuery)

    var paramIndex = 1

    renderedParams.forEach { param ->
        if (param == null) {
            preparedStatement.setNull(paramIndex++)
        } else {
            preparedStatement.setObject(paramIndex, param)
        }
    }

    return preparedStatement
}

package com.linecorp.kotlinjdsl.executor.spring.jdbc.sql

import com.linecorp.kotlinjdsl.querymodel.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParams
import com.linecorp.kotlinjdsl.render.sql.SqlRenderer
import com.linecorp.kotlinjdsl.render.sql.setting.ParamType
import com.linecorp.kotlinjdsl.render.sql.setting.SqlRenderSetting
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import kotlin.reflect.KClass

private val sqlRendererForJdbcTemplate = SqlRenderer(
    setting = SqlRenderSetting(
        paramType = ParamType.INDEXED,
    ),
)

private val sqlRendererForNamedParameterJdbcTemplate = SqlRenderer(
    setting = SqlRenderSetting(
        paramType = ParamType.NAMED,
    ),
)

inline fun <reified T : Any> JdbcTemplate.queryForObject(
    query: SqlQuery<*>,
    context: RenderContext,
): T? {
    return queryForObject(query, context, T::class)
}

fun <T : Any> JdbcTemplate.queryForObject(
    query: SqlQuery<*>,
    context: RenderContext,
    requiredType: KClass<T>,
): T? {
    val rendered = sqlRendererForJdbcTemplate.render(query, context)

    val renderedQuery = rendered.query
    val renderedParams = rendered.params as SqlRenderedParams.Indexed

    return queryForObject(renderedQuery, requiredType.java, *renderedParams.toTypedArray())
}

inline fun <reified T : Any> NamedParameterJdbcTemplate.queryForObject(
    query: SqlQuery<*>,
    context: RenderContext,
): T? {
    return queryForObject(query, context, T::class)
}

fun <T : Any> NamedParameterJdbcTemplate.queryForObject(
    query: SqlQuery<*>,
    context: RenderContext,
    requiredType: KClass<T>,
): T? {
    val rendered = sqlRendererForNamedParameterJdbcTemplate.render(query, context)

    val renderedQuery = rendered.query
    val renderedParams = rendered.params as SqlRenderedParams.Named

    return queryForObject(renderedQuery, renderedParams, requiredType.java)
}

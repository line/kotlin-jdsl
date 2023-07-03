package com.linecorp.kotlinjdsl.executor.spring.jpa.sql

import com.linecorp.kotlinjdsl.query.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParams
import com.linecorp.kotlinjdsl.render.sql.SqlRenderer
import com.linecorp.kotlinjdsl.render.sql.setting.ParamType
import com.linecorp.kotlinjdsl.render.sql.setting.SqlRenderSetting
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import kotlin.reflect.KClass

private val sqlRenderer = SqlRenderer(
    setting = SqlRenderSetting(
        paramType = ParamType.INDEXED,
    ),
)

fun EntityManager.createNativeQuery(
    query: SqlQuery<*>,
    context: RenderContext,
): Query {
    val rendered = sqlRenderer.render(query, context)

    val renderedQuery = rendered.query
    val renderedParams = rendered.params

    val jpaQuery = createNativeQuery(renderedQuery)

    when (renderedParams) {
        is SqlRenderedParams.Indexed -> jpaQuery.setParameters(renderedParams)
        is SqlRenderedParams.Named -> jpaQuery.setParameters(renderedParams)
    }

    return jpaQuery
}

fun <T : Any> EntityManager.createNativeQuery(
    query: SqlQuery<*>,
    context: RenderContext,
    resultClass: KClass<T>,
): Query {
    val rendered = sqlRenderer.render(query, context)

    val renderedQuery = rendered.query
    val renderedParams = rendered.params

    val jpaQuery = createNativeQuery(renderedQuery, resultClass.java)

    when (renderedParams) {
        is SqlRenderedParams.Indexed -> jpaQuery.setParameters(renderedParams)
        is SqlRenderedParams.Named -> jpaQuery.setParameters(renderedParams)
    }

    return jpaQuery
}

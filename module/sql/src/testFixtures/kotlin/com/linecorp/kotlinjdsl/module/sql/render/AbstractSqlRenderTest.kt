package com.linecorp.kotlinjdsl.module.sql.render

import com.linecorp.kotlinjdsl.module.sql.SqlConfiguration
import com.linecorp.kotlinjdsl.module.sql.SqlModule
import com.linecorp.kotlinjdsl.querymodel.sql.SqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.SqlRendered
import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParams
import com.linecorp.kotlinjdsl.render.sql.SqlRenderer
import com.linecorp.kotlinjdsl.render.sql.setting.ParamType
import com.linecorp.kotlinjdsl.render.sql.setting.SqlRenderSetting
import org.assertj.core.api.WithAssertions

abstract class AbstractSqlRenderTest : WithAssertions {
    protected val context: RenderContext by lazy(LazyThreadSafetyMode.NONE) {
        SqlConfiguration().apply { registerModules(modules) }.getConfiguredContext()
    }

    private val renderer: SqlRenderer by lazy(LazyThreadSafetyMode.NONE) {
        SqlRenderer(
            setting = renderSetting,
        )
    }

    private val modules = mutableListOf<SqlModule>()
    private var renderSetting: SqlRenderSetting = SqlRenderSetting(
        paramType = ParamType.INDEXED,
    )

    protected fun registerRenderSetting(renderSetting: SqlRenderSetting) {
        this.renderSetting = renderSetting
    }

    protected fun registerModule(module: SqlModule) {
        modules.add(module)
    }

    protected fun render(query: SqlQuery<*>): SqlRendered {
        return renderer.render(query, context)
    }

    protected fun indexedParams(init: IndexedParamsDsl.() -> Unit): SqlRenderedParams {
        val params = mutableListOf<Any?>()

        IndexedParamsDsl(params).init()

        return SqlRenderedParams.Indexed(params)
    }

    protected fun namedParams(init: NamedParamsDsl.() -> Unit): SqlRenderedParams {
        val params = mutableMapOf<String, Any?>()

        NamedParamsDsl(params).init()

        return SqlRenderedParams.Named(params)
    }

    class IndexedParamsDsl(
        private val params: MutableList<Any?>
    ) {
        fun param(value: Any?) {
            params.add(value)
        }
    }

    class NamedParamsDsl(
        private val params: MutableMap<String, Any?>
    ) {
        fun param(name: String, value: Any?) {
            params[name] = value
        }
    }
}

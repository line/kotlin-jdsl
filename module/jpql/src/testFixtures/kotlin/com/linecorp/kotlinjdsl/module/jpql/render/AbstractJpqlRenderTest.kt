package com.linecorp.kotlinjdsl.module.jpql.render

import com.linecorp.kotlinjdsl.module.jpql.JpqlConfiguration
import com.linecorp.kotlinjdsl.module.jpql.JpqlModule
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import org.assertj.core.api.WithAssertions

abstract class AbstractJpqlRenderTest : WithAssertions {
    private val context: RenderContext by lazy(LazyThreadSafetyMode.NONE) {
        JpqlConfiguration().apply { registerModules(modules) }.getConfiguredContext()
    }

    private val renderer: JpqlRenderer by lazy(LazyThreadSafetyMode.NONE) {
        JpqlRenderer()
    }

    private val modules = mutableListOf<JpqlModule>()

    protected fun registerModule(module: JpqlModule) {
        modules.add(module)
    }

    protected fun render(query: JpqlQuery<*>): JpqlRendered {
        return renderer.render(query, context)
    }
}

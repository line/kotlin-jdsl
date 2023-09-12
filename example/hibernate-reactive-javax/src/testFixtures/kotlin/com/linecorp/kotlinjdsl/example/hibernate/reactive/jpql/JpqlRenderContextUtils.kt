package com.linecorp.kotlinjdsl.example.hibernate.reactive.jpql

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext

object JpqlRenderContextUtils {
    fun getJpqlRenderContext(): JpqlRenderContext {
        return jpqlRenderContext
    }
}

private val jpqlRenderContext = JpqlRenderContext()

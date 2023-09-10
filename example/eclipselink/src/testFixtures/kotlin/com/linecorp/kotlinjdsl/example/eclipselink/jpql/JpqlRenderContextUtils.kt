package com.linecorp.kotlinjdsl.example.eclipselink.jpql

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext

object JpqlRenderContextUtils {
    fun getJpqlRenderContext(): JpqlRenderContext {
        return jpqlRenderContext
    }
}

private val jpqlRenderContext = JpqlRenderContext()

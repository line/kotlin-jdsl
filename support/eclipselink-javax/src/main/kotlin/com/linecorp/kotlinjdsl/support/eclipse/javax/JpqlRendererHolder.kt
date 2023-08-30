package com.linecorp.kotlinjdsl.support.eclipse.javax

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer

internal object JpqlRendererHolder {
    private var renderer = JpqlRenderer()

    fun get(): JpqlRenderer {
        return renderer
    }

    fun set(renderer: JpqlRenderer) {
        JpqlRendererHolder.renderer = renderer
    }
}

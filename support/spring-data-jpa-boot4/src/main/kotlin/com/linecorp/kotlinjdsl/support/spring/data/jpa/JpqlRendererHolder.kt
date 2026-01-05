package com.linecorp.kotlinjdsl.support.spring.data.jpa

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer

internal object JpqlRendererHolder {
    private var renderer = JpqlRenderer()

    fun get(): JpqlRenderer = renderer

    fun set(renderer: JpqlRenderer) {
        JpqlRendererHolder.renderer = renderer
    }
}

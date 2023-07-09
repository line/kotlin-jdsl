package com.linecorp.kotlinjdsl.render.jpql

data class JpqlRenderedParams(
    private val delegate: Map<String, Any?>
) : Map<String, Any?> by delegate

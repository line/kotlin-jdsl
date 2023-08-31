package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
data class JpqlRenderedParams(
    private val delegate: Map<String, Any?>,
) : Map<String, Any?> by delegate

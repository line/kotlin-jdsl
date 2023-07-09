package com.linecorp.kotlinjdsl.render.jpql

data class JpqlRendered(
    val query: String,
    val params: JpqlRenderedParams,
)

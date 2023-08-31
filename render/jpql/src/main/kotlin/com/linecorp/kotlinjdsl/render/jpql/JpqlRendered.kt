package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
data class JpqlRendered(
    @SinceJdsl("3.0.0")
    val query: String,

    @SinceJdsl("3.0.0")
    val params: JpqlRenderedParams,
)

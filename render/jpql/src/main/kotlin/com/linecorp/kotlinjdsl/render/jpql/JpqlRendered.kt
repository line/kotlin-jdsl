package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Result of rendering JPQL.
 *
 * @property query the rendered query.
 * @property params the parameters included in the rendered query.
 */
@SinceJdsl("3.0.0")
data class JpqlRendered(
    @SinceJdsl("3.0.0")
    val query: String,

    @SinceJdsl("3.0.0")
    val params: JpqlRenderedParams,
)

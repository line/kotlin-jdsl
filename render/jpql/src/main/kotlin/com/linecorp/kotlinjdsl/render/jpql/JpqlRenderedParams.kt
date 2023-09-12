package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Parameters included in the rendered query.
 * This is the named parameter of the query, with the parameter name as the key and the parameter value as the value.
 */
@SinceJdsl("3.0.0")
data class JpqlRenderedParams(
    private val delegate: Map<String, Any?>,
) : Map<String, Any?> by delegate

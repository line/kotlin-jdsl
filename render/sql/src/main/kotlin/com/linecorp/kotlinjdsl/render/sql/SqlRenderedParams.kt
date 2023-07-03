package com.linecorp.kotlinjdsl.render.sql

sealed interface SqlRenderedParams {
    data class Indexed(
        private val delegate: List<Any?>
    ) : List<Any?> by delegate, SqlRenderedParams

    data class Named(
        private val delegate: Map<String, Any?>
    ) : Map<String, Any?> by delegate, SqlRenderedParams
}

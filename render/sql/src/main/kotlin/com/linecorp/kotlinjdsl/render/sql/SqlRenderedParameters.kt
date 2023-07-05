package com.linecorp.kotlinjdsl.render.sql

sealed interface SqlRenderedParameters {
    data class Indexed(
        private val delegate: List<Any?>
    ) : List<Any?> by delegate, SqlRenderedParameters

    data class Named(
        private val delegate: Map<String, Any?>
    ) : Map<String, Any?> by delegate, SqlRenderedParameters
}

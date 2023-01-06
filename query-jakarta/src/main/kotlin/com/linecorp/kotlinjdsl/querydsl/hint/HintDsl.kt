package com.linecorp.kotlinjdsl.querydsl.hint

interface HintDsl {
    fun sqlHints(vararg hints: String) = sqlHints(hints.toList())
    fun sqlHints(hints: List<String>)

    fun hints(vararg hints: Pair<String, Any>) = hints(hints.toMap())
    fun hints(hints: Map<String, Any>)
}

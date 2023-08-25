package com.linecorp.kotlinjdsl.render.jpql.writer

interface JpqlWriter {
    fun write(string: String)

    fun writeIfAbsent(string: String)

    fun <T> writeEach(
        iterable: Iterable<T>,
        separator: String = ", ",
        prefix: String = "",
        postfix: String = "",
        write: (T) -> Unit,
    )

    fun writeParentheses(inner: () -> Unit)

    fun writeParam(value: Any?)

    fun writeParam(name: String, value: Any?)
}

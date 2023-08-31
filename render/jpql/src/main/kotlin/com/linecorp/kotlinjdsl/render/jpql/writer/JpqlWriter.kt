package com.linecorp.kotlinjdsl.render.jpql.writer

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface JpqlWriter {
    @SinceJdsl("3.0.0")
    fun write(string: String)

    @SinceJdsl("3.0.0")
    fun writeIfAbsent(string: String)

    @SinceJdsl("3.0.0")
    fun <T> writeEach(
        iterable: Iterable<T>,
        separator: String = ", ",
        prefix: String = "",
        postfix: String = "",
        write: (T) -> Unit,
    )

    @SinceJdsl("3.0.0")
    fun writeParentheses(inner: () -> Unit)

    @SinceJdsl("3.0.0")
    fun writeParam(value: Any?)

    @SinceJdsl("3.0.0")
    fun writeParam(name: String, value: Any?)
}

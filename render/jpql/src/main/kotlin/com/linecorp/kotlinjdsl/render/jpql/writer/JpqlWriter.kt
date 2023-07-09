package com.linecorp.kotlinjdsl.render.jpql.writer

interface JpqlWriter {
    fun write(short: Short)

    fun write(int: Int)

    fun write(long: Long)

    fun write(float: Float)

    fun write(double: Double)

    fun write(boolean: Boolean)

    fun write(string: CharSequence)

    fun <T> writeEach(
        iterable: Iterable<T>,
        separator: CharSequence = ", ",
        prefix: CharSequence = "",
        postfix: CharSequence = "",
        write: (T) -> Unit,
    )

    fun <T> writeEach(
        iterable: Iterable<T>,
        separator: () -> Unit = {},
        prefix: () -> Unit = {},
        postfix: () -> Unit = {},
        write: (T) -> Unit,
    )

    fun writeKeyword(clause: CharSequence)

    fun writeParam(value: Any?)

    fun writeParam(name: String, value: Any?)
}

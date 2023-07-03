package com.linecorp.kotlinjdsl.render.sql.generator

interface SqlWriter {
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

    fun writeValue(value: Any?)

    fun writeValue(name: String, value: Any?)
}

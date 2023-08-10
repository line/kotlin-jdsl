package com.linecorp.kotlinjdsl.render.jpql.writer

interface JpqlWriter {
    fun write(int: Int)

    fun write(long: Long)

    fun write(float: Float)

    fun write(double: Double)

    fun write(boolean: Boolean)

    fun write(string: String)

    fun writeIfAbsent(string: String)

    fun <T> writeEach(
        iterable: Iterable<T>,
        separator: String = ", ",
        prefix: String = "",
        postfix: String = "",
        write: (T) -> Unit,
    )

    fun writeParam(value: Any?)

    fun writeParam(name: String, value: Any?)
}

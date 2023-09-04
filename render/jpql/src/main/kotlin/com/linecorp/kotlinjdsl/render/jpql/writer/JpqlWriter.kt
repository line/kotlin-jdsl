package com.linecorp.kotlinjdsl.render.jpql.writer

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Writer used to create a JPQL query.
 */
@SinceJdsl("3.0.0")
interface JpqlWriter {
    /**
     * Writes the string.
     */
    @SinceJdsl("3.0.0")
    fun write(string: String)

    /**
     * Writes the string if the same string was not written last.
     */
    @SinceJdsl("3.0.0")
    fun writeIfAbsent(string: String)

    /**
     * Writes elements in iterable.
     *
     * @param iterable The elements to write.
     * @param separator It is inserted between each element.
     * @param prefix It is inserted before the first element.
     * @param postfix It is inserted after the last element.
     * @param write It is called for each element.
     */
    @SinceJdsl("3.0.0")
    fun <T> writeEach(
        iterable: Iterable<T>,
        separator: String = ", ",
        prefix: String = "",
        postfix: String = "",
        write: (T) -> Unit,
    )

    /**
     * Writes parentheses.
     * They are only written once if they are redundant.
     * For example, the ((value)) is written as (value).
     */
    @SinceJdsl("3.0.0")
    fun writeParentheses(inner: () -> Unit)

    /**
     * Writes a named parameter without a name.
     * The name is generated automatically.
     */
    @SinceJdsl("3.0.0")
    fun writeParam(value: Any?)

    /**
     * Writes a named parameter with a name.
     */
    @SinceJdsl("3.0.0")
    fun writeParam(name: String, value: Any?)
}

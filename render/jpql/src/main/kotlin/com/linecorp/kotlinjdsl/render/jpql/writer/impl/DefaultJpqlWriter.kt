package com.linecorp.kotlinjdsl.render.jpql.writer.impl

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter

class DefaultJpqlWriter : JpqlWriter {
    private val stringBuilder: StringBuilder = StringBuilder()
    private val params = mutableMapOf<String, Any?>()

    private val incrementer: Incrementer = Incrementer()

    override fun write(string: CharSequence) {
        stringBuilder.append(string)
    }

    override fun write(short: Short) {
        stringBuilder.append(short)
    }

    override fun write(int: Int) {
        stringBuilder.append(int)
    }

    override fun write(long: Long) {
        stringBuilder.append(long)
    }

    override fun write(float: Float) {
        stringBuilder.append(float)
    }

    override fun write(double: Double) {
        stringBuilder.append(double)
    }

    override fun write(boolean: Boolean) {
        stringBuilder.append(boolean)
    }

    override fun <T> writeEach(
        iterable: Iterable<T>,
        separator: CharSequence,
        prefix: CharSequence,
        postfix: CharSequence,
        write: (T) -> Unit
    ) {
        write(prefix)

        for ((index, element) in iterable.withIndex()) {
            if (index > 0) {
                write(separator)
            }

            write(element)
        }

        write(postfix)
    }

    override fun <T> writeEach(
        iterable: Iterable<T>,
        separator: () -> Unit,
        prefix: () -> Unit,
        postfix: () -> Unit,
        write: (T) -> Unit
    ) {
        prefix()

        for ((index, element) in iterable.withIndex()) {
            if (index > 0) {
                separator()
            }

            write(element)
        }

        postfix()
    }

    override fun writeKeyword(clause: CharSequence) {
        if (stringBuilder.isNotEmpty()) {
            stringBuilder.append(" ").append(clause).append(" ")
        } else {
            stringBuilder.append(clause).append(" ")
        }
    }

    override fun writeParam(value: Any?) {
        val name = incrementer.getNext().toString()

        write(":${name}")
        params[name] = value
    }

    override fun writeParam(name: String, value: Any?) {
        if (!name.startsWith(":")) {
            val prefixedName = ":${name}"

            write(prefixedName)
            params[name] = value
        } else {
            val nonPrefixedName = name.substring(1)

            write(name)
            params[nonPrefixedName] = value
        }
    }

    fun getQuery(): String {
        return stringBuilder.toString()
    }

    fun getParams(): JpqlRenderedParams {
        return JpqlRenderedParams(params)
    }

    private inner class Incrementer {
        private var counter: Int = 0

        fun getNext(): Int {
            return ++counter
        }
    }
}

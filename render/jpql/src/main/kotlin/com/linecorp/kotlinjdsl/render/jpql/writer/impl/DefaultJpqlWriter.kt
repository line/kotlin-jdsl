package com.linecorp.kotlinjdsl.render.jpql.writer.impl

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter

class DefaultJpqlWriter(
    params: Map<String, Any?>,
) : JpqlWriter {
    private val stringBuilder: StringBuilder = StringBuilder()
    private val params: MutableMap<String, Any?> = mutableMapOf()

    init {
        params.forEach { (name, value) -> addParamValue(name, value) }
    }

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

        writeParam(name, value)
    }

    override fun writeParam(name: String, value: Any?) {
        writeParamName(name)
        addParamValue(name, value)
    }

    private fun writeParamName(name: String) {
        val prefixedName = if (name.startsWith(":")) {
            name
        } else {
            ":${name}"
        }

        write(prefixedName)
    }

    private fun addParamValue(name: String, value: Any?) {
        val nonPrefixedName = if (name.startsWith(":")) {
            name.substring(1)
        } else {
            name
        }

        params.putIfAbsent(nonPrefixedName, value)
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

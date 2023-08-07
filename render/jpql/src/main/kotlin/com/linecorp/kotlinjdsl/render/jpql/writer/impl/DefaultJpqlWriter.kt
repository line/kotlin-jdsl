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

    override fun write(string: String) {
        stringBuilder.append(string)
    }

    override fun writeIfAbsent(string: String) {
        if (!stringBuilder.endsWith(string)) {
            stringBuilder.append(string)
        }
    }

    override fun <T> writeEach(
        iterable: Iterable<T>,
        separator: String,
        prefix: String,
        postfix: String,
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

    override fun writeParam(value: Any?) {
        val name = "param${incrementer.getNext()}"

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

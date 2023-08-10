package com.linecorp.kotlinjdsl.render.jpql.writer.impl

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter

internal class DefaultJpqlWriter(
    params: Map<String, Any?>,
) : JpqlWriter {
    private val stringBuilder: StringBuilder = StringBuilder()
    private val params: MutableMap<String, Any?> = mutableMapOf()

    private val incrementer: Incrementer

    init {
        val paramNumber = params.keys.mapNotNull {
            numberSuffixRegex.find(it)?.value?.toInt()
        }.maxOrNull()

        incrementer = if (paramNumber == null) {
            Incrementer(initial = 1)
        } else {
            Incrementer(initial = paramNumber + 1)
        }

        params.forEach { (name, value) -> putParamValue(name, value) }
    }

    override fun write(int: Int) {
        stringBuilder.append(int)
    }

    override fun write(long: Long) {
        stringBuilder.append(long).append("L")
    }

    override fun write(float: Float) {
        stringBuilder.append(float).append("F")
    }

    override fun write(double: Double) {
        stringBuilder.append(double)
    }

    override fun write(boolean: Boolean) {
        if (boolean) {
            stringBuilder.append("TRUE")
        } else {
            stringBuilder.append("FALSE")
        }
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
        write: (T) -> Unit,
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
        putParamValue(name, value)
    }

    private fun writeParamName(name: String) {
        val prefixedName = if (name.startsWith(":")) {
            name
        } else {
            ":${name}"
        }

        write(prefixedName)
    }

    private fun putParamValue(name: String, value: Any?) {
        val nonPrefixedName = if (name.startsWith(":")) {
            name.substring(1)
        } else {
            name
        }

        if (!params.containsKey(nonPrefixedName)) {
            params[nonPrefixedName] = value
        }
    }

    fun getQuery(): String {
        return stringBuilder.toString()
    }

    fun getParams(): JpqlRenderedParams {
        return JpqlRenderedParams(params)
    }

    private inner class Incrementer(
        initial: Int,
    ) {
        private var counter: Int = initial

        fun getNext(): Int {
            return counter++
        }
    }
}

private val numberSuffixRegex = Regex("[0-9]+$")

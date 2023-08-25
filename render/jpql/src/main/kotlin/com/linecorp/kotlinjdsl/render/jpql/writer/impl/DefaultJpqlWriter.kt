package com.linecorp.kotlinjdsl.render.jpql.writer.impl

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter

internal class DefaultJpqlWriter private constructor(
    private var internal: InternalJpqlWriter,
) : JpqlWriter {
    constructor(params: Map<String, Any?>) : this(InternalJpqlWriter(params))

    override fun write(string: String) {
        internal.write(string)
    }

    override fun writeIfAbsent(string: String) {
        internal.writeIfAbsent(string)
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

    override fun writeParentheses(inner: () -> Unit) {
        val origin = internal

        internal = InternalJpqlWriter(origin.incrementer)

        inner()

        val innerQuery = internal.stringBuilder.toString()
        val innerParams = internal.params

        internal = origin

        if (innerQuery.startsWith("(") && innerQuery.endsWith(")")) {
            write(innerQuery)
        } else {
            write("(")
            write(innerQuery)
            write(")")
        }

        internal.putAllParams(innerParams)
    }

    override fun writeParam(value: Any?) {
        internal.writeParam(value)
    }

    override fun writeParam(name: String, value: Any?) {
        internal.writeParam(name, value)
    }

    fun getQuery(): String {
        return internal.stringBuilder.toString()
    }

    fun getParams(): JpqlRenderedParams {
        return JpqlRenderedParams(internal.params)
    }
}

private class InternalJpqlWriter(
    val params: MutableMap<String, Any?>,
    val incrementer: Incrementer,
) {
    constructor(incrementer: Incrementer) : this(mutableMapOf(), incrementer)

    init {
        params.forEach { (name, value) -> putParamValue(name, value) }
    }

    val stringBuilder: StringBuilder = StringBuilder()

    fun write(string: String) {
        stringBuilder.append(string)
    }

    fun writeIfAbsent(string: String) {
        if (!stringBuilder.endsWith(string)) {
            stringBuilder.append(string)
        }
    }

    fun writeParam(value: Any?) {
        val name = "param${incrementer.getNext()}"

        writeParam(name, value)
    }

    fun writeParam(name: String, value: Any?) {
        writeParamName(name)
        putParamValue(name, value)
    }

    fun putAllParams(params: Map<String, Any?>) {
        params.forEach { (name, value) -> putParamValue(name, value) }
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
}

private fun InternalJpqlWriter(params: Map<String, Any?>): InternalJpqlWriter {
    val paramNumber = params.keys.mapNotNull {
        suffixNumber.find(it)?.value?.toInt()
    }.maxOrNull()

    val incrementer = if (paramNumber == null) {
        Incrementer(initial = 1)
    } else {
        Incrementer(initial = paramNumber + 1)
    }

    return InternalJpqlWriter(params.toMutableMap(), incrementer)
}

private class Incrementer(
    initial: Int
) {
    private var counter: Int = initial

    fun getNext(): Int {
        return counter++
    }
}

private val suffixNumber = Regex("[0-9]+$")

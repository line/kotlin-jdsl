package com.linecorp.kotlinjdsl.render.jpql.writer.impl

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter

internal class DefaultJpqlWriter private constructor(
    private var internal: InternalJpqlWriter,
) : JpqlWriter {
    constructor(params: Map<String, Any?>) : this(InternalJpqlWriter(params))

    private var nodes: Nodes = Nodes()

    override fun write(string: String) {
        internal.write(string)
        nodes.add(Node.String())
    }

    override fun writeIfAbsent(string: String) {
        if (!internal.stringBuilder.endsWith(string)) {
            write(string)
        }
    }

    override fun <T> writeEach(
        iterable: Iterable<T>,
        separator: String,
        write: (T) -> Unit,
    ) {
        for ((index, element) in iterable.withIndex()) {
            if (index > 0) {
                write(separator)
            }

            write(element)
        }
    }

    override fun writeParentheses(inner: () -> Unit) {
        val originWriter = internal
        val innerWriter = InternalJpqlWriter(originWriter.incrementer)

        internal = innerWriter

        val parentheses = Node.Parentheses()

        val open = parentheses.open
        nodes.add(open)

        inner()

        val close = parentheses.close
        nodes.add(close)

        val nextOfOpen = open.next
        val previousOfClose = close.previous

        val innerQuery = innerWriter.stringBuilder.toString()
        val innerParams = innerWriter.params

        if (
            nextOfOpen is Node.Parenthesis &&
            previousOfClose is Node.Parenthesis &&
            nextOfOpen.isSibling(previousOfClose)
        ) {
            originWriter.write(innerQuery)
        } else {
            originWriter.write("(")
            originWriter.write(innerQuery)
            originWriter.write(")")
        }

        originWriter.putAllParams(innerParams)

        internal = originWriter
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

    private class Nodes {
        private var current: Node = Node.Null()

        fun add(node: Node) {
            current.let {
                it.next = node
                node.previous = it
            }

            current = node
        }
    }

    private abstract class Node {
        var previous: Node? = null
        var next: Node? = null

        class Null : Node()

        class String : Node()

        class Parentheses {
            val open: OpenParenthesis = OpenParenthesis(this)
            val close: CloseParenthesis = CloseParenthesis(this)
        }

        abstract class Parenthesis(
            private val parentheses: Parentheses,
        ) : Node() {
            fun isSibling(other: Parenthesis): Boolean {
                return this.parentheses == other.parentheses
            }
        }

        class OpenParenthesis(parentheses: Parentheses) : Parenthesis(parentheses)
        class CloseParenthesis(parentheses: Parentheses) : Parenthesis(parentheses)
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
            ":$name"
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
    initial: Int,
) {
    private var counter: Int = initial

    fun getNext(): Int {
        return counter++
    }
}

private val suffixNumber = Regex("[0-9]+$")

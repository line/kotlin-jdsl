package com.linecorp.kotlinjdsl.render.sql.writer.impl

import com.linecorp.kotlinjdsl.render.sql.SqlRenderedParams
import com.linecorp.kotlinjdsl.render.sql.setting.ParamType
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter

class DefaultSqlWriter(
    paramType: ParamType,
) : SqlWriter {
    private val stringBuilder: StringBuilder = StringBuilder()

    private val valueWriter: ValueWriter = when (paramType) {
        ParamType.INDEXED -> IndexedValueWriter()
        ParamType.NAMED -> NamedValueWriter()
    }

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

    override fun writeValue(value: Any?) {
        valueWriter.writeValue(value)
    }

    override fun writeValue(name: String, value: Any?) {
        valueWriter.writeValue(name, value)
    }

    fun getQuery(): String {
        return stringBuilder.toString()
    }

    fun getParams(): SqlRenderedParams {
        return valueWriter.getParams()
    }

    private inner class Incrementer {
        private var counter: Int = 0

        fun getNext(): Int {
            return ++counter
        }
    }

    private interface ValueWriter {
        fun writeValue(value: Any?)
        fun writeValue(name: String, value: Any?)

        fun getParams(): SqlRenderedParams
    }

    private inner class IndexedValueWriter : ValueWriter {
        private val params = mutableListOf<Any?>()

        override fun writeValue(value: Any?) {
            write("?")
            params.add(value)
        }

        override fun writeValue(name: String, value: Any?) {
            writeValue(value)
            params.add(value)
        }

        override fun getParams(): SqlRenderedParams {
            return SqlRenderedParams.Indexed(params)
        }
    }

    private inner class NamedValueWriter : ValueWriter {
        private val incrementer: Incrementer = Incrementer()

        private val params = mutableMapOf<String, Any?>()

        override fun writeValue(value: Any?) {
            val name = ":${incrementer.getNext()}"

            write(name)
            params[name] = value
        }

        override fun writeValue(name: String, value: Any?) {
            write(name)
            params[name] = value
        }

        override fun getParams(): SqlRenderedParams {
            return SqlRenderedParams.Named(params)
        }
    }
}

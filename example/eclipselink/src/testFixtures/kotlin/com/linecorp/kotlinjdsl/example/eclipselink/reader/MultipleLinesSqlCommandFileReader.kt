package com.linecorp.kotlinjdsl.example.eclipselink.reader

import java.io.FileReader

internal class MultipleLinesSqlCommandFileReader(
    fileName: String,
) : FileReader(fileName) {
    override fun read(): Int {
        val data = super.read()

        return if (data == -1) {
            -1
        } else {
            when (val aChar = data.toChar()) {
                ';' -> '\n'
                '\n' -> ' '
                else -> aChar
            }.code
        }
    }
}

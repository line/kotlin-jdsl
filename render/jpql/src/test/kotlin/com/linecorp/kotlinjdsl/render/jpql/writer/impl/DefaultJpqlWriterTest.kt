package com.linecorp.kotlinjdsl.render.jpql.writer.impl

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class DefaultJpqlWriterTest : WithAssertions {
    private val sut = DefaultJpqlWriter(emptyMap())

    private val string1 = "string1"
    private val string2 = "string2"
    private val string3 = "string3"

    private val paramKey1 = "paramKey1"
    private val paramKey2 = "paramKey2"
    private val paramValue1 = "paramValue1"
    private val paramValue2 = "paramValue2"
    private val paramValue3 = "paramValue3"
    private val paramValue4 = "paramValue4"

    @Test
    fun `constructor() has all params, when the params is not empty`() {
        // given
        val sut = DefaultJpqlWriter(
            mapOf(
                paramKey1 to paramValue1,
                paramKey2 to paramValue2,
            ),
        )

        // when
        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).containsAllEntriesOf(mapOf(paramKey1 to paramValue1, paramKey2 to paramValue2))
        assertThat(actualQuery).isEqualTo("")
    }

    @Test
    fun writeIfAbsent() {
        // when
        sut.writeIfAbsent(string1)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo(string1)
    }

    @Test
    fun `writeIfAbsent() does not print the string, when preprinted string contains the string`() {
        // given
        sut.write(string1)

        // when
        sut.writeIfAbsent(string1)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo(string1)
    }

    @Test
    fun writeEach() {
        // given
        val strings = listOf(string1, string2, string3)

        // when
        sut.writeEach(strings, separator = ", ") {
            sut.write(it)
        }

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("$string1, $string2, $string3")
    }

    @Test
    fun writeParentheses() {
        // when
        sut.writeParentheses {
            sut.write(string1)
        }

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("($string1)")
    }

    @Test
    fun `writeParentheses() prints only one parentheses, when there are redundant parentheses`() {
        // when
        sut.writeParentheses {
            sut.writeParentheses {
                sut.write(string1)
            }
        }

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("($string1)")
    }

    @Test
    fun `writeParentheses() prints redundant parentheses, when there are redundant parentheses were added manually`() {
        // when
        sut.writeParentheses {
            sut.write("(")
            sut.write(string1)
            sut.write(")")
        }

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("(($string1))")
    }

    @Test
    fun `writeParentheses() prints all parentheses, when there are no redundant parentheses`() {
        // when
        sut.writeParentheses {
            sut.writeParentheses {
                sut.write(string1)
            }
            sut.write(" ")
            sut.write(string2)
            sut.write(" ")
            sut.writeParentheses {
                sut.write(string3)
            }
        }

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("(($string1) $string2 ($string3))")
    }

    @Test
    fun `writeParentheses() has all params written inside writeParentheses()`() {
        // when
        sut.writeParentheses {
            sut.writeParam(paramValue1)

            sut.write(" ")

            sut.writeParentheses {
                sut.write(string1)
            }

            sut.write(" ")

            sut.writeParam(paramValue2)
        }

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).containsAllEntriesOf(
            mapOf(
                "param1" to paramValue1,
                "param2" to paramValue2,
            ),
        )
        assertThat(actualQuery).isEqualTo("(:param1 ($string1) :param2)")
    }

    @Test
    fun writeParam() {
        // when
        sut.writeParam(paramValue1)
        sut.writeParam(paramValue2)
        sut.writeParam(paramValue3)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).containsAllEntriesOf(
            mapOf(
                "param1" to paramValue1,
                "param2" to paramValue2,
                "param3" to paramValue3,
            ),
        )
        assertThat(actualQuery).isEqualTo(":param1:param2:param3")
    }

    @Test
    fun `writeParam() prints the param with an auto increment from initial param, when there are an initial param`() {
        // given
        val sut = DefaultJpqlWriter(
            mapOf(
                "param5" to paramValue1,
                "param3" to paramValue2,
                "param1" to paramValue3,
            ),
        )

        // when
        sut.writeParam("param5", null)
        sut.writeParam("param3", null)
        sut.writeParam("param1", null)
        sut.writeParam(paramValue4)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).containsAllEntriesOf(
            mapOf(
                "param5" to paramValue1,
                "param3" to paramValue2,
                "param1" to paramValue3,
                "param6" to paramValue4,
            ),
        )
        assertThat(actualQuery).isEqualTo(":param5:param3:param1:param6")
    }

    @Test
    fun `writeParam() cannot override an initial params, when an initial params exist`() {
        // given
        val sut = DefaultJpqlWriter(
            mapOf(
                "paramA" to paramValue1,
                "paramB" to paramValue2,
                "paramC" to null,
            ),
        )

        // when
        sut.writeParam("paramA", null)
        sut.writeParam("paramB", paramValue3)
        sut.writeParam("paramC", paramValue4)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).containsAllEntriesOf(
            mapOf(
                "paramA" to paramValue1,
                "paramB" to paramValue2,
                "paramC" to null,
            ),
        )
        assertThat(actualQuery).isEqualTo(":paramA:paramB:paramC")
    }
}

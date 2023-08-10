package com.linecorp.kotlinjdsl.render.jpql.writer.impl

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class DefaultJpqlWriterTest : WithAssertions {
    private val sut = DefaultJpqlWriter(emptyMap())

    private val paramKey1 = "paramKey1"
    private val paramKey2 = "paramKey2"
    private val paramValue1 = "paramValue1"
    private val paramValue2 = "paramValue2"
    private val paramValue3 = "paramValue3"
    private val paramValue4 = "paramValue4"

    @Test
    fun `constructor - WHEN params is not empty, THEN put all params`() {
        // given
        val sut = DefaultJpqlWriter(
            mapOf(
                paramKey1 to paramValue1,
                paramKey2 to paramValue2,
            )
        )

        // when
        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).containsAllEntriesOf(mapOf(paramKey1 to paramValue1, paramKey2 to paramValue2))
        assertThat(actualQuery).isEqualTo("")
    }

    @Test
    fun `write - int`() {
        // when
        sut.write(1)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("1")
    }

    @Test
    fun `write - long`() {
        // when
        sut.write(1L)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("1L")
    }

    @Test
    fun `write - float`() {
        // when
        sut.write(1F)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("1.0F")
    }

    @Test
    fun `write - double`() {
        // when
        sut.write(1.0)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("1.0")
    }

    @Test
    fun `write - true`() {
        // when
        sut.write(true)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("TRUE")
    }

    @Test
    fun `write - false`() {
        // when
        sut.write(false)

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("FALSE")
    }

    @Test
    fun `writeIfAbsent - WHEN last appended string is not input, THEN prints input`() {
        // when
        sut.writeIfAbsent("string1")

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("string1")
    }

    @Test
    fun `writeIfAbsent - WHEN last appended string is input, THEN does not print input`() {
        // given
        sut.write("string1")

        // when
        sut.writeIfAbsent("string1")

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("string1")
    }

    @Test
    fun writeEach() {
        // given
        val strings = listOf("string1", "string2", "string3")

        // when
        sut.writeEach(strings, separator = ", ", prefix = "(", postfix = ")") {
            sut.write(it)
        }

        val actualParam = sut.getParams()
        val actualQuery = sut.getQuery()

        // then
        assertThat(actualParam).isEmpty()
        assertThat(actualQuery).isEqualTo("(string1, string2, string3)")
    }

    @Test
    fun `writeParam - WHEN there is no param name, THEN print param with auto increment name`() {
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
            )
        )
        assertThat(actualQuery).isEqualTo(":param1:param2:param3")
    }

    @Test
    fun `writeParam - WHEN there are initial param with number suffix, THEN print param with auto increment from initial param`() {
        // given
        val sut = DefaultJpqlWriter(
            mapOf(
                "param5" to paramValue1,
                "param3" to paramValue2,
                "param1" to paramValue3,
            )
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
            )
        )
        assertThat(actualQuery).isEqualTo(":param5:param3:param1:param6")
    }

    @Test
    fun `writeParam - WHEN initial params exist, THEN it does not override initial params`() {
        // given
        val sut = DefaultJpqlWriter(
            mapOf(
                "paramA" to paramValue1,
                "paramB" to paramValue2,
                "paramC" to null,
            )
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
            )
        )
        assertThat(actualQuery).isEqualTo(":paramA:paramB:paramC")
    }
}

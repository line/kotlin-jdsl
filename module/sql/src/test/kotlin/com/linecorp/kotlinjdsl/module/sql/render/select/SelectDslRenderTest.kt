package com.linecorp.kotlinjdsl.module.sql.render.select

import com.linecorp.kotlinjdsl.dsl.sql.Normal
import com.linecorp.kotlinjdsl.dsl.sql.sql
import com.linecorp.kotlinjdsl.module.sql.render.AbstractSqlRenderTest
import com.linecorp.kotlinjdsl.querymodel.sql.SelectQuery
import com.linecorp.kotlinjdsl.render.sql.SqlRendered
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SelectDslRenderTest : AbstractSqlRenderTest() {
    private val value1 = 1
    private val value2 = 2
    private val value3 = 3

    @Test
    @DisplayName("SELECT value")
    fun select1() {
        // given
        val select: SelectQuery = sql(Normal) {
            select(
                value1,
                value2,
                value3,
            )
        }

        // when
        val actual = render(select)

        // then
        val expected = SqlRendered(
            query = "SELECT ?, ?, ?",
            params = indexedParams {
                param(value1)
                param(value2)
                param(value3)
            },
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("SELECT param")
    fun select2() {
        // given
        val select: SelectQuery = sql(Normal) {
            select(
                param(value1),
                param(value2),
                param(value3),
            )
        }

        // when
        val actual = render(select)

        // then
        val expected = SqlRendered(
            query = "SELECT ?, ?, ?",
            params = indexedParams {
                param(value1)
                param(value2)
                param(value3)
            },
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("SELECT *")
    fun select3() {
        // given
        val select: SelectQuery = sql(Normal) {
            select(
                asterisk(),
            )
        }

        // when
        val actual = render(select)

        // then
        val expected = SqlRendered(
            query = "SELECT *",
            params = indexedParams { },
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("SELECT table.*")
    fun select4() {
        // given
        val select: SelectQuery = sql(Normal) {
            select(
                table(Table1::class).asterisk(),
                table(Table2::class).asterisk(),
                table(Table3::class).asterisk(),
            )
        }

        // when
        val actual = render(select)

        // then
        val expected = SqlRendered(
            query = "SELECT Table1.*, Table2.*, Table3.*",
            params = indexedParams { },
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("SELECT table.col")
    fun select5() {
        // given
        val select: SelectQuery = sql(Normal) {
            select(
                table(Table1::class).col(Table1::column1),
                col(table(Table1::class), Table1::column2),
                col(Table1::column3),
            )
        }

        // when
        val actual = render(select)

        // then
        val expected = SqlRendered(
            query = "SELECT Table1.column1, Table1.column2, Table1.column3",
            params = indexedParams { },
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("SELECT table.column")
    fun select6() {
        // given
        val select: SelectQuery = sql(Normal) {
            select(
                table(Table1::class).column(Table1::column1),
                column(table(Table1::class), Table1::column2),
                column(Table1::column3),
            )
        }

        // when
        val actual = render(select)

        // then
        val expected = SqlRendered(
            query = "SELECT Table1.column1, Table1.column2, Table1.column3",
            params = indexedParams { },
        )

        assertThat(actual).isEqualTo(expected)
    }

    interface Table1 {
        val column1: Byte
        val column2: Short
        val column3: Int
        val column4: Long
        val column5: Float
        val column6: Double
        val column7: Char
        val column8: Boolean
        val column9: String
    }

    interface Table2 {
        val column1: Byte
        val column2: Short
        val column3: Int
        val column4: Long
        val column5: Float
        val column6: Double
        val column7: Char
        val column8: Boolean
        val column9: String
    }

    interface Table3 {
        val column1: Byte
        val column2: Short
        val column3: Int
        val column4: Long
        val column5: Float
        val column6: Double
        val column7: Char
        val column8: Boolean
        val column9: String
    }
}

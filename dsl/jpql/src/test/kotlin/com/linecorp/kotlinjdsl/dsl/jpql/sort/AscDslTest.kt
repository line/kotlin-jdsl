package com.linecorp.kotlinjdsl.dsl.jpql.sort

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import org.junit.jupiter.api.Test

class AscDslTest : AbstractJpqlDslTest() {
    @Test
    fun `expression asc`() {
        // when
        val sort = testJpql {
            path(TestTable::int1).asc()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.asc(
            expr = Paths.path(TestTable::int1),
            nullOrder = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression asc`() {
        // when
        val sort = testJpql {
            path(TestTable::nullableInt1).asc()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.asc(
            expr = Paths.path(TestTable::nullableInt1),
            nullOrder = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression desc`() {
        // when
        val sort = testJpql {
            path(TestTable::int1).desc()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.desc(
            expr = Paths.path(TestTable::int1),
            nullOrder = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression desc`() {
        // when
        val sort = testJpql {
            path(TestTable::nullableInt1).desc()
        }.toSort()

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.desc(
            expr = Paths.path(TestTable::nullableInt1),
            nullOrder = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        var int1: Int = 1
        var nullableInt1: Int? = null
    }
}

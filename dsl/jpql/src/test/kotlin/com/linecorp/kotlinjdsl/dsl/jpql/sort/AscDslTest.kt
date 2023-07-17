package com.linecorp.kotlinjdsl.dsl.jpql.sort

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Sorts
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import org.junit.jupiter.api.Test

class AscDslTest : AbstractJpqlDslTest() {
    @Test
    fun `expression asc`() {
        // when
        val sort = testJpql {
            path(TestTable::int1).asc()
        }

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.asc(
            Paths.path(TestTable::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression asc`() {
        // when
        val sort = testJpql {
            path(TestTable::nullableInt1).asc()
        }

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.asc(
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression desc`() {
        // when
        val sort = testJpql {
            path(TestTable::int1).desc()
        }

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.desc(
            Paths.path(TestTable::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression desc`() {
        // when
        val sort = testJpql {
            path(TestTable::nullableInt1).desc()
        }

        val actual: Sort = sort // for type check

        // then
        val expected = Sorts.desc(
            Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        var int1: Int = 1
        var nullableInt1: Int? = null
    }
}

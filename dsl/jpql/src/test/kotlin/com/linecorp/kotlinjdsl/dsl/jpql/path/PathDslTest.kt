package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import org.junit.jupiter.api.Test

class PathDslTest : AbstractJpqlDslTest() {
    @Test
    fun `path property`() {
        // when
        val path = testJpql {
            path(TestRoot::table1)
        }

        val actual: Path<TestTable> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(TestRoot::table1),
        )
    }

    @Test
    fun `path nullable property`() {
        // when
        val path = testJpql {
            path(TestRoot::nullableTable1)
        }

        val actual: Path<TestTable?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(TestRoot::nullableTable1),
        )
    }

    @Test
    fun `path property path property`() {
        // when
        val path = testJpql {
            path(TestRoot::table1).path(TestTable::int1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable>(TestRoot::table1), TestTable::int1),
        )
    }

    @Test
    fun `path property path nullable property`() {
        // when
        val path = testJpql {
            path(TestRoot::table1).path(TestTable::nullableInt1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable>(TestRoot::table1), TestTable::nullableInt1),
        )
    }

    @Test
    fun `path nullable property path property`() {
        // when
        val path = testJpql {
            path(TestRoot::nullableTable1).path(TestTable::int1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable?>(TestRoot::nullableTable1), TestTable::int1),
        )
    }

    @Test
    fun `path nullable property path nullable property`() {
        // when
        val path = testJpql {
            path(TestRoot::nullableTable1).path(TestTable::nullableInt1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable?>(TestRoot::nullableTable1), TestTable::nullableInt1),
        )
    }

    @Test
    fun `path property invoke property`() {
        // when
        val path = testJpql {
            path(TestRoot::table1)(TestTable::int1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable>(TestRoot::table1), TestTable::int1),
        )
    }

    @Test
    fun `path property invoke nullable property`() {
        // when
        val path = testJpql {
            path(TestRoot::table1)(TestTable::nullableInt1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable>(TestRoot::table1), TestTable::nullableInt1),
        )
    }

    @Test
    fun `path nullable property invoke property`() {
        // when
        val path = testJpql {
            path(TestRoot::nullableTable1)(TestTable::int1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable?>(TestRoot::nullableTable1), TestTable::int1),
        )
    }

    @Test
    fun `path nullable property invoke nullable property`() {
        // when
        val path = testJpql {
            path(TestRoot::nullableTable1)(TestTable::nullableInt1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path<TestTable?>(TestRoot::nullableTable1), TestTable::nullableInt1),
        )
    }

    private class TestRoot {
        val table1: TestTable = TestTable()

        val nullableTable1: TestTable? = null
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}

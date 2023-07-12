package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
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
            Field<TestTable>(
                TestTable::class,
                AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                TestRoot::table1.name,
            ),
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
            Field<TestTable>(
                TestTable::class,
                AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                TestRoot::nullableTable1.name,
            ),
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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::table1.name,
                ),
                TestTable::int1.name,
            ),
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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::table1.name,
                ),
                TestTable::nullableInt1.name,
            ),

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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::nullableTable1.name,
                ),
                TestTable::int1.name,
            ),
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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::nullableTable1.name,
                ),
                TestTable::nullableInt1.name,
            ),
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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::table1.name,
                ),
                TestTable::int1.name,
            ),

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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::table1.name,
                ),
                TestTable::nullableInt1.name,
            ),

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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::nullableTable1.name,
                ),
                TestTable::int1.name,
            ),
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
            Field<Int>(
                Int::class,
                Field<TestTable>(
                    TestTable::class,
                    AliasedPath(Entity(TestRoot::class), TestRoot::class.simpleName!!),
                    TestRoot::nullableTable1.name,
                ),
                TestTable::nullableInt1.name,
            ),
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

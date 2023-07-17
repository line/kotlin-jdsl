package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import org.junit.jupiter.api.Test

class AliasDslTest : AbstractJpqlDslTest() {
    private val alias1 = "alias1"
    private val alias2 = "alias2"

    @Test
    fun `path as string`() {
        // when
        val path = testJpql {
            path(TestTable::int1).`as`(alias1)
        }

        val actual: Path<Int> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::int1),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable path as string`() {
        // when
        val path = testJpql {
            path(TestTable::nullableInt1).`as`(alias1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::nullableInt1),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path alias string`() {
        // when
        val path = testJpql {
            path(TestTable::int1).alias(alias1)
        }

        val actual: Path<Int> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::int1),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable path alias string`() {
        // when
        val path = testJpql {
            path(TestTable::nullableInt1).alias(alias1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::nullableInt1),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path as string as string`() {
        // when
        val path = testJpql {
            path(TestTable::int1).`as`(alias1).`as`(alias2)
        }

        val actual: Path<Int> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::int1),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable path as string as string`() {
        // when
        val path = testJpql {
            path(TestTable::nullableInt1).`as`(alias1).`as`(alias2)
        }

        val actual: Path<Int?> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::nullableInt1),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path alias string alias string`() {
        // when
        val path = testJpql {
            path(TestTable::int1).alias(alias1).alias(alias2)
        }

        val actual: Path<Int> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::int1),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable path alias string alias string`() {
        // when
        val path = testJpql {
            path(TestTable::nullableInt1).alias(alias1).alias(alias2)
        }

        val actual: Path<Int?> = path // for type check

        // then
        val expected = Paths.alias(
            Paths.path(TestTable::nullableInt1),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path as string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1)).`as`(alias1)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).`as`(alias1))
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::table1),
                alias1,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join nullable path as string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1)).`as`(alias1)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).`as`(alias1))
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::nullableTable1),
                alias1,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join path alias string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1)).alias(alias1)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).alias(alias1))
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::table1),
                alias1,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join nullable path alias string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1)).alias(alias1)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).alias(alias1))
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::nullableTable1),
                alias1,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join path as string as string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1)).`as`(alias1).`as`(alias2)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).`as`(alias1)).`as`(alias2)
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::table1),
                alias2,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join nullable path as string as string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1)).`as`(alias1).`as`(alias2)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).`as`(alias1)).`as`(alias2)
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::nullableTable1),
                alias2,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity path alias string alias string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1)).alias(alias1).alias(alias2)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).alias(alias1)).alias(alias2)
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::table1),
                alias2,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join nullable path alias string alias string`() {
        // when
        val path1 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1)).alias(alias1).alias(alias2)
        }

        val actual1: Path<Any> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).alias(alias1)).alias(alias2)
        }

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable::class),
            right = Paths.alias(
                Paths.path(TestTable::nullableTable1),
                alias2,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
        val nullableInt1: Int? = null

        val table1: OtherTable = OtherTable()
        val nullableTable1: OtherTable? = null
    }

    private class OtherTable
}

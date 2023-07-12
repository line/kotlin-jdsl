package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
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
        val expected = AliasedPath<Int>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = AliasedPath<Int?>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = AliasedPath<Int>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = AliasedPath<Int?>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = AliasedPath<Int>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = AliasedPath<Int?>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = AliasedPath<Int>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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
        val expected = AliasedPath<Int?>(
            Field(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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

        val actual1: Path<OtherTable> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).`as`(alias1))
        }

        val actual2: Path<OtherTable> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::table1.name,
                ),
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

        val actual1: Path<OtherTable?> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).`as`(alias1))
        }

        val actual2: Path<OtherTable?> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableTable1.name,
                ),
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

        val actual1: Path<OtherTable> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).alias(alias1))
        }

        val actual2: Path<OtherTable> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::table1.name,
                ),
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

        val actual1: Path<OtherTable?> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).alias(alias1))
        }

        val actual2: Path<OtherTable?> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableTable1.name,
                ),
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

        val actual1: Path<OtherTable> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).`as`(alias1)).`as`(alias2)
        }

        val actual2: Path<OtherTable> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::table1.name,
                ),
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

        val actual1: Path<OtherTable?> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).`as`(alias1)).`as`(alias2)
        }

        val actual2: Path<OtherTable?> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableTable1.name,
                ),
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

        val actual1: Path<OtherTable> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::table1).alias(alias1)).alias(alias2)
        }

        val actual2: Path<OtherTable> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::table1.name,
                ),
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

        val actual1: Path<OtherTable?> = path1 // for type check

        val path2 = testJpql {
            entity(TestTable::class).join(path(TestTable::nullableTable1).alias(alias1)).alias(alias2)
        }

        val actual2: Path<OtherTable?> = path2 // for type check

        // then
        val expected = Join<OtherTable>(
            left = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            right = AliasedPath(
                Field(
                    OtherTable::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableTable1.name,
                ),
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
    fun `expression as string`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)).`as`(alias1)
        }

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = AliasedExpression(
            Count(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                distinct = false,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression as string`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)).`as`(alias1)
        }

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = AliasedExpression<Int?>(
            Max(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                distinct = false,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression alias string`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)).alias(alias1)
        }

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = AliasedExpression(
            Count(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                distinct = false,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression alias string`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)).alias(alias1)
        }

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = AliasedExpression<Int?>(
            Max(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                distinct = false,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression as string as string`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)).`as`(alias1).`as`(alias2)
        }

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = AliasedExpression(
            Count(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                distinct = false,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression as string as string`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)).`as`(alias1).`as`(alias2)
        }

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = AliasedExpression<Int?>(
            Max(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                distinct = false,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression alias string alias string`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)).alias(alias1).alias(alias2)
        }

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = AliasedExpression(
            Count(
                Field<Int>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                distinct = false,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable expression alias string alias string`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt1)).alias(alias1).alias(alias2)
        }

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = AliasedExpression<Int?>(
            Max(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                distinct = false,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null

        val table1: OtherTable = OtherTable()

        val nullableTable1: OtherTable? = null
    }

    private class OtherTable
}

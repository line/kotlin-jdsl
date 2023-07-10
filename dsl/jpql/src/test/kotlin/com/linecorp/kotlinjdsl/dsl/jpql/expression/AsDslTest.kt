package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
import org.junit.jupiter.api.Test

class AsDslTest : AbstractJpqlDslTest() {
    private val alias1 = "alias1"

    @Test
    fun `path as alias`() {
        // when
        val path = testJpql {
            path(TestTable::int1).`as`(alias1)
        }

        val actual: Path<Int> = path // for type check

        // then
        val expected = AliasedPath<Int>(
            Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable path as alias`() {
        // when
        val path = testJpql {
            path(TestTable::nullableInt).`as`(alias1)
        }

        val actual: Path<Int?> = path // for type check

        // then
        val expected = AliasedPath<Int?>(
            Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt.name,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun `expression as alias`() {
        // when
        val expression = testJpql {
            count(path(TestTable::int1)).`as`(alias1)
        }

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = AliasedExpression(
            Count(
                Field<Int>(
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
    fun `nullable expression as alias`() {
        // when
        val expression = testJpql {
            max(path(TestTable::nullableInt)).`as`(alias1)
        }

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = AliasedExpression<Int?>(
            Max(
                Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt.name,
                ),
                distinct = false,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt: Int? = null
    }
}

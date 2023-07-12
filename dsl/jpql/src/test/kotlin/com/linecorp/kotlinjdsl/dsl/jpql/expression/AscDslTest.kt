package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.SortExpression
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
        val expected = SortExpression(
            Field<Int>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            Sort.Order.ASC,
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
        val expected = SortExpression(
            Field<Int>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            Sort.Order.ASC,
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
        val expected = SortExpression(
            Field<Int>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
            Sort.Order.DESC,
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
        val expected = SortExpression(
            Field<Int>(
                Int::class,
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
            Sort.Order.DESC,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        var int1: Int = 1

        var nullableInt1: Int? = null
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.CustomExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import org.junit.jupiter.api.Test

class CustomExpressionDslTest : AbstractJpqlDslTest() {
    private val template1: String = "template1"

    @Test
    fun `customExpression type template`() {
        // when
        val expression = testJpql {
            customExpression<Int>(template1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = CustomExpression<Int>(
            template1,
            emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression type template expression`() {
        // when
        val expression = testJpql {
            customExpression<String>(template1, path(TestTable::int1))
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = CustomExpression<Int>(
            template1,
            listOf(
                Field<Int?>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression type template nullable expression`() {
        // when
        val expression = testJpql {
            customExpression<String>(template1, path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = CustomExpression<Int>(
            template1,
            listOf(
                Field<Int?>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression nullable type template`() {
        // when
        val expression = testJpql {
            customExpression<Int?>(template1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = CustomExpression<Int>(
            template1,
            emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression nullable type template expression`() {
        // when
        val expression = testJpql {
            customExpression<String?>(template1, path(TestTable::int1))
        }.toExpression()

        val actual: Expression<String?> = expression // for type check

        // then
        val expected = CustomExpression<Int>(
            template1,
            listOf(
                Field<Int?>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression nullable type template nullable expression`() {
        // when
        val expression = testJpql {
            customExpression<String?>(template1, path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<String?> = expression // for type check

        // then
        val expected = CustomExpression<Int>(
            template1,
            listOf(
                Field<Int?>(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1

        val nullableInt1: Int? = null
    }
}

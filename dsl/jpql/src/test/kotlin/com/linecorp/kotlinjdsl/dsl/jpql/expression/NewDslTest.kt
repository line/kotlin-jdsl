package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class NewDslTest : AbstractJpqlDslTest() {
    @Test
    fun `new type int expression`() {
        // when
        val expression = testJpql {
            new(Row::class, path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::int1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `new type int expression int expression`() {
        // when
        val expression = testJpql {
            new(Row::class, path(TestTable::int1), path(TestTable::int2))
        }.toExpression()

        val actual: Expression<Row> = expression // for type check

        // then
        val expected = Expressions.new(
            Row::class,
            listOf(
                Paths.path(TestTable::int1),
                Paths.path(TestTable::int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
        val int2: Int = 1
    }

    private class Row
}

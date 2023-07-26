package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class MaxDslTest : AbstractJpqlDslTest() {
    @Test
    fun `max int property`() {
        // when
        val expression = testJpql {
            max(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `maxDistinct int property`() {
        // when
        val expression = testJpql {
            maxDistinct(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max int expression`() {
        // when
        val expression = testJpql {
            max(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `maxDistinct int expression`() {
        // when
        val expression = testJpql {
            maxDistinct(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
    }
}

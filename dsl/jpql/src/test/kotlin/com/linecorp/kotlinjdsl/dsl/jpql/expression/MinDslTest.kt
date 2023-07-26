package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class MinDslTest : AbstractJpqlDslTest() {
    @Test
    fun `min int property`() {
        // when
        val expression = testJpql {
            min(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `minDistinct int property`() {
        // when
        val expression = testJpql {
            minDistinct(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min int expression`() {
        // when
        val expression = testJpql {
            min(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `minDistinct int expression`() {
        // when
        val expression = testJpql {
            minDistinct(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
    }
}

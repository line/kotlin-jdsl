package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class CountDslTest : AbstractJpqlDslTest() {
    @Test
    fun `count int property`() {
        // when
        val expression = testJpql {
            count(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `countDistinct int property`() {
        // when
        val expression = testJpql {
            countDistinct(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count int expression`() {
        // when
        val expression = testJpql {
            count(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `countDistinct int expression`() {
        // when
        val expression = testJpql {
            countDistinct(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
    }
}

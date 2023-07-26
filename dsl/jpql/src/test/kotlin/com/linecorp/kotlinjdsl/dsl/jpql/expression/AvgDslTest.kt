package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class AvgDslTest : AbstractJpqlDslTest() {
    @Test
    fun `avg int property`() {
        // when
        val expression = testJpql {
            avg(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avgDistinct int property`() {
        // when
        val expression = testJpql {
            avgDistinct(TestTable1::int1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg int expression`() {
        // when
        val expression = testJpql {
            avg(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = false,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avgDistinct int expression`() {
        // when
        val expression = testJpql {
            avgDistinct(path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = true,
            Paths.path(TestTable1::int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
    }
}

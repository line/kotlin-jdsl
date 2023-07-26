package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class CustomExpressionDslTest : AbstractJpqlDslTest() {
    private val template1: String = "template1"

    @Test
    fun `customExpression type template`() {
        // when
        val expression = testJpql {
            customExpression(Int::class, template1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.customExpression(
            Int::class,
            template1,
            emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression type template int expression`() {
        // when
        val expression = testJpql {
            customExpression(Int::class, template1, path(TestTable1::int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.customExpression(
            Int::class,
            template1,
            listOf(
                Paths.path(TestTable1::int1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customExpression type template int expression int expression`() {
        // when
        val expression = testJpql {
            customExpression(Int::class, template1, path(TestTable1::int1), path(TestTable1::int2))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.customExpression(
            Int::class,
            template1,
            listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable1::int2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
        val int2: Int? = null
    }
}

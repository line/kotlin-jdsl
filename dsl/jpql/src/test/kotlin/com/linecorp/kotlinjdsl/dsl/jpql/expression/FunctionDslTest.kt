package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class FunctionDslTest : AbstractJpqlDslTest() {
    private val functionName1: String = "functionName1"

    @Test
    fun `function type template`() {
        // when
        val expression = testJpql {
            function(Int::class, functionName1, listOf(path(TestTable1::int1)))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.function(
            Int::class,
            functionName1,
            listOf(
                Paths.path(TestTable1::int1),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PowerDslTest : WithAssertions {
    private val baseExpression = Paths.path(Employee::age)
    private val exponentExpression = Expressions.value(2)
    private val exponentPrimitive = 2

    @Test
    fun `power() with a property`() {
        // when
        val expression1 = queryPart {
            power(Employee::age, exponentPrimitive)
        }.toExpression()

        val expression2 = queryPart {
            power(Employee::age, exponentExpression)
        }.toExpression()

        val actual1: Expression<Int> = expression1 // for type check
        val actual2: Expression<Int> = expression2 // for type check

        // then
        val expected1 = Expressions.power(
            base = Paths.path(Employee::age),
            exponent = Expressions.value(exponentPrimitive),
        )

        val expected2 = Expressions.power(
            base = Paths.path(Employee::age),
            exponent = exponentExpression,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `power() with a expression`() {
        // when
        val expression1 = queryPart {
            power(baseExpression, exponentPrimitive)
        }.toExpression()

        val expression2 = queryPart {
            power(baseExpression, exponentExpression)
        }.toExpression()

        val actual1: Expression<Int> = expression1 // for type check
        val actual2: Expression<Int> = expression2 // for type check

        // then
        val expected1 = Expressions.power(
            base = baseExpression,
            exponent = Expressions.value(exponentPrimitive),
        )

        val expected2 = Expressions.power(
            base = baseExpression,
            exponent = exponentExpression,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PowerDslTest : WithAssertions {
    private val intExpression1 = Paths.path(Employee::age)
    private val intExpression2 = Expressions.value(2)

    private val int1 = 2

    @Test
    fun `power() with a property`() {
        // when
        val expression1 = queryPart {
            power(Employee::age, int1)
        }.toExpression()

        val expression2 = queryPart {
            power(Employee::age, intExpression2)
        }.toExpression()

        val actual1: Expression<Number> = expression1 // for type check
        val actual2: Expression<Number> = expression2 // for type check

        // then
        val expected1 = Expressions.power(
            base = Paths.path(Employee::age),
            exponent = Expressions.value(int1),
        )

        val expected2 = Expressions.power(
            base = Paths.path(Employee::age),
            exponent = intExpression2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `power() with a expression`() {
        // when
        val expression1 = queryPart {
            power(intExpression1, int1)
        }.toExpression()

        val expression2 = queryPart {
            power(intExpression1, intExpression2)
        }.toExpression()

        val actual1: Expression<Number> = expression1 // for type check
        val actual2: Expression<Number> = expression2 // for type check

        // then
        val expected1 = Expressions.power(
            base = intExpression1,
            exponent = Expressions.value(int1),
        )

        val expected2 = Expressions.power(
            base = intExpression1,
            exponent = intExpression2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class ModDslTest : WithAssertions {
    private val intExpression1 = Paths.path(Employee::age)
    private val intExpression2 = Expressions.value(2)

    private val int1 = 2

    @Test
    fun `mod() with a property`() {
        // when
        val expression1 = queryPart {
            mod(Employee::age, int1)
        }.toExpression()

        val expression2 = queryPart {
            mod(Employee::age, intExpression1)
        }.toExpression()

        val actual1: Expression<Int> = expression1 // for type check
        val actual2: Expression<Int> = expression2 // for type check

        // then
        val expected1 = Expressions.mod(
            value1 = Paths.path(Employee::age),
            value2 = Expressions.value(int1),
        )

        val expected2 = Expressions.mod(
            value1 = Paths.path(Employee::age),
            value2 = intExpression1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `mod() with a expression`() {
        // when
        val expression1 = queryPart {
            mod(intExpression1, int1)
        }.toExpression()

        val expression2 = queryPart {
            mod(intExpression1, intExpression2)
        }.toExpression()

        val actual1: Expression<Int> = expression1 // for type check
        val actual2: Expression<Int> = expression2 // for type check

        // then
        val expected1 = Expressions.mod(
            value1 = intExpression1,
            value2 = Expressions.value(int1),
        )

        val expected2 = Expressions.mod(
            value1 = intExpression1,
            value2 = intExpression2,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }
}

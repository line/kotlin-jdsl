package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import kotlin.reflect.KClass
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class TypeDslTest : WithAssertions {
    private val entity1 = Entities.entity(Employee::class)

    private val path1 = Paths.path(EmployeeDepartment::employee)

    @Test
    fun `type() with entity`() {
        // when
        val expression = queryPart {
            type(entity1)
        }.toExpression()

        val actual: Expression<KClass<*>> = expression // for type check

        // then
        val expected = Expressions.type(
            entity1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `type() with path`() {
        // when
        val expression = queryPart {
            type(path1)
        }.toExpression()

        val actual: Expression<KClass<*>> = expression // for type check

        // then
        val expected = Expressions.type(
            path1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

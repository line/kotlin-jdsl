package com.linecorp.kotlinjdsl.benchmark.sample.query.select

import com.linecorp.kotlinjdsl.benchmark.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

object SelectQuery3 : () -> SelectQuery<*> {
    override fun invoke(): SelectQuery<*> {
        data class DerivedEntity(
            val employeeId: Long,
            val count: Long,
        )

        return jpql {
            val subquery = select<DerivedEntity>(
                path(Employee::employeeId).`as`(expression("employeeId")),
                count(Employee::employeeId).`as`(expression("count")),
            ).from(
                entity(Employee::class),
                join(Employee::departments),
            ).groupBy(
                path(Employee::employeeId),
            ).having(
                count(Employee::employeeId).greaterThan(1L),
            )

            select(
                count(DerivedEntity::employeeId),
            ).from(
                subquery.asEntity(),
            )
        }
    }
}

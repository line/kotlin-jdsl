package com.linecorp.kotlinjdsl.benchmark.sample.query.update

import com.linecorp.kotlinjdsl.benchmark.entity.department.Department
import com.linecorp.kotlinjdsl.benchmark.entity.employee.Employee
import com.linecorp.kotlinjdsl.benchmark.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

object UpdateQuery1 : () -> UpdateQuery<*> {
    override fun invoke(): UpdateQuery<*> {
        return jpql {
            val employeeIds = select<Long>(
                path(EmployeeDepartment::employee)(Employee::employeeId),
            ).from(
                entity(Department::class),
                join(EmployeeDepartment::class)
                    .on(path(Department::departmentId).equal(path(EmployeeDepartment::departmentId))),
            ).where(
                path(Department::name).like("%03"),
            ).asSubquery()

            update(
                Employee::class,
            ).set(
                path(Employee::nickname),
                path(Employee::name),
            ).whereAnd(
                path(Employee::nickname).isNull(),
                path(Employee::employeeId).`in`(employeeIds),
            )
        }
    }
}

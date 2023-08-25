package com.linecorp.kotlinjdsl.benchmark.sample.query.delete

import com.linecorp.kotlinjdsl.benchmark.sample.entity.department.Department
import com.linecorp.kotlinjdsl.benchmark.sample.entity.employee.Employee
import com.linecorp.kotlinjdsl.benchmark.sample.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery

object DeleteQuery1 : () -> DeleteQuery<*> {
    override fun invoke(): DeleteQuery<*> {
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

            deleteFrom(
                entity(Employee::class),
            ).where(
                path(Employee::employeeId).`in`(employeeIds),
            )
        }
    }
}

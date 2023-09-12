package com.linecorp.kotlinjdsl.render.jpql.entity.employee

abstract class Employee(
    val employeeId: Long,
    val name: String,
    val nickname: String?,
    val phone: String,
    val address: EmployeeAddress,
    val departments: MutableSet<EmployeeDepartment>,
)

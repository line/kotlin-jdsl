package com.linecorp.kotlinjdsl.support.spring.batch.entity.employee

abstract class Employee(
    val employeeId: Long,
    val name: String,
    val nickname: String?,
    val phone: String,
    val address: EmployeeAddress,
    val departments: MutableSet<EmployeeDepartment>,
)

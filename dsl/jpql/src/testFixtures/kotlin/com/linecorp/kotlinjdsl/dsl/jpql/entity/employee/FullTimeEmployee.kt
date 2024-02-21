package com.linecorp.kotlinjdsl.dsl.jpql.entity.employee

import java.math.BigDecimal

class FullTimeEmployee(
    employeeId: Long,
    name: String,
    nickname: String?,
    age: Int,
    phone: String,
    address: EmployeeAddress,
    departments: MutableSet<EmployeeDepartment>,
    val annualSalary: BigDecimal,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    age = age,
    phone = phone,
    address = address,
    departments = departments,
) {
    fun getMonthlySalary() = annualSalary.div(BigDecimal.valueOf(12))
}

@file:Suppress("unused")

package com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee

import java.math.BigDecimal

class PartTimeEmployee(
    employeeId: Long,
    name: String,
    nickname: String?,
    phone: String,
    address: EmployeeAddress,
    departments: MutableSet<EmployeeDepartment>,
    val weeklySalary: BigDecimal,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    phone = phone,
    address = address,
    departments = departments,
)

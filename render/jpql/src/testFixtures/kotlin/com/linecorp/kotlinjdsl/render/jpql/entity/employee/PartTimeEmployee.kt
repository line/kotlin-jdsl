package com.linecorp.kotlinjdsl.render.jpql.entity.employee

import java.math.BigDecimal

class PartTimeEmployee(
    employeeId: Long,
    name: String,
    nickname: String?,
    age: Int,
    phone: String,
    address: EmployeeAddress,
    departments: MutableSet<EmployeeDepartment>,
    val weeklySalary: BigDecimal,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    age = age,
    phone = phone,
    address = address,
    departments = departments,
)

package com.linecorp.kotlinjdsl.render.jpql.entity.employee

import java.math.BigDecimal

class FullTimeEmployee(
    employeeId: Long,
    name: String,
    nickname: String?,
    phone: String,
    address: EmployeeAddress,
    departments: MutableSet<EmployeeDepartment>,
    age: Int,
    val annualSalary: BigDecimal,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    phone = phone,
    address = address,
    departments = departments,
    age = age,
)

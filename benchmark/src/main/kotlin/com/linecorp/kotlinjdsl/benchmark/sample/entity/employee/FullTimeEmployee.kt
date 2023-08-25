package com.linecorp.kotlinjdsl.benchmark.sample.entity.employee

import jakarta.persistence.*

@DiscriminatorValue("FULL_TIME")
@Entity
class FullTimeEmployee(
    employeeId: Long,

    name: String,

    nickname: String?,

    phone: String,

    address: EmployeeAddress,

    departments: MutableSet<EmployeeDepartment>,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "annual_salary"))
    var annualSalary: EmployeeSalary,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    phone = phone,
    address = address,
    departments = departments,
)

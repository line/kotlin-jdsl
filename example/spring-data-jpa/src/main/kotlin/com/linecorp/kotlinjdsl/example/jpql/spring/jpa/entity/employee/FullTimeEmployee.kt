package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.employee

import jakarta.persistence.*

@Entity
@DiscriminatorValue("FULL_TIME")
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
) {
    override fun toString(): String = "FullTimeEmployee(employeeId=$employeeId)"
}

package com.linecorp.kotlinjdsl.example.spring.batch.entity.employee

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

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

@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.eclipselink.entity.employee

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("PART_TIME")
class PartTimeEmployee(
    employeeId: Long,

    name: String,

    nickname: String?,

    phone: String,

    address: EmployeeAddress,

    departments: MutableSet<EmployeeDepartment>,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "weekly_salary"))
    val weeklySalary: EmployeeSalary,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    phone = phone,
    address = address,
    departments = departments,
) {
    override fun toString(): String = "PartTimeEmployee(weeklySalary=$weeklySalary)"
}

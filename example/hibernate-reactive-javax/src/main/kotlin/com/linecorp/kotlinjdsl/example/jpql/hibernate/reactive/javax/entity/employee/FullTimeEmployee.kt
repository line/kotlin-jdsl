package com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.employee

import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Embedded
import javax.persistence.Entity

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

package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.employee

import jakarta.persistence.*

@Entity
@DiscriminatorValue("FULL_TIME")
class FullTimeEmployee(
    override val employeeId: Long,

    override var name: String,

    override var nickname: String?,

    override var phone: String,

    override var address: EmployeeAddress,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "annual_salary"))
    var annualSalary: EmployeeSalary,

    @OneToMany(mappedBy = "employee")
    override val departments: MutableSet<EmployeeDepartment>,
) : Employee(
    employeeId = employeeId,
    name = name,
    nickname = nickname,
    phone = phone,
    address = address,
    departments = departments,
)

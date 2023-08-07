package com.linecorp.kotlinjdsl.example.jpql.springjpa.entity.employee

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type")
class Employee(
    @Id
    @Column(name = "employee_id")
    val employeeId: Long,

    @Column(name = "name")
    var name: String,

    @Column(name = "nickname")
    var nickname: String?,

    @Column(name = "phone")
    var phone: String,

    @Embedded
    val address: EmployeeAddress,

    @OneToMany(mappedBy = "employee")
    val departments: MutableSet<EmployeeDepartment>,
) {
    override fun equals(other: Any?): Boolean = Objects.equals(employeeId, (other as? Employee)?.employeeId)
    override fun hashCode(): Int = Objects.hashCode(employeeId)

    override fun toString(): String {
        return "Employee(employeeId=$employeeId, name='$name')"
    }
}

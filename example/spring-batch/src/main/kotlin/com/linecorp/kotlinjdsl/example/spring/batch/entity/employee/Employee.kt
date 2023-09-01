@file:Suppress("LeakingThis", "unused", "JpaDataSourceORMInspection")

package com.linecorp.kotlinjdsl.example.spring.batch.entity.employee

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
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
    init {
        departments.forEach { it.employee = this }
    }

    override fun equals(other: Any?): Boolean = Objects.equals(employeeId, (other as? Employee)?.employeeId)
    override fun hashCode(): Int = Objects.hashCode(employeeId)

    override fun toString(): String = "Employee(employeeId=$employeeId)"
}

@file:Suppress("LeakingThis", "unused", "JpaDataSourceORMInspection")

package com.linecorp.kotlinjdsl.example.eclipselink.javax.entity.employee

import java.util.*
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.OneToMany
import javax.persistence.Table

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

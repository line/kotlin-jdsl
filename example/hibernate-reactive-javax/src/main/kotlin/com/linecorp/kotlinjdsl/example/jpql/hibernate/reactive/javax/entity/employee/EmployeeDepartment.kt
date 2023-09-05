@file:Suppress("JpaDataSourceORMInspection")

package com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.employee

import com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.annotation.CompositeId
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "employee_department")
@IdClass(EmployeeDepartment.EmployeeDepartmentId::class)
class EmployeeDepartment(
    @Id
    @Column(name = "department_id")
    val departmentId: Long,
) {
    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    lateinit var employee: Employee

    private val employeeDepartmentId get() = EmployeeDepartmentId(employee.employeeId, departmentId)

    override fun equals(other: Any?): Boolean =
        Objects.equals(employeeDepartmentId, (other as? EmployeeDepartment)?.employeeDepartmentId)

    override fun hashCode(): Int =
        Objects.hashCode(employeeDepartmentId)

    override fun toString(): String =
        "EmployeeDepartment(employeeDepartmentId=$employeeDepartmentId)"

    @CompositeId
    data class EmployeeDepartmentId(
        val employee: Long,
        val departmentId: Long,
    ) : Serializable
}

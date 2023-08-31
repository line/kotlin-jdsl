package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.annotation.CompositeId
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
    @ManyToOne
    @JoinColumn(name = "employee_id")
    val employee: Employee,

    @Id
    @Column(name = "department_id")
    val departmentId: Long,
) {
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

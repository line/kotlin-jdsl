package com.linecorp.kotlinjdsl.example.spring.batch.entity.employee

import com.linecorp.kotlinjdsl.example.spring.batch.annotation.CompositeId
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

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

    @CompositeId
    data class EmployeeDepartmentId(
        val employee: Long,
        val departmentId: Long,
    ) : Serializable
}


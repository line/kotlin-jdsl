@file:Suppress("JpaDataSourceORMInspection")

package com.linecorp.kotlinjdsl.example.eclipselink.entity.department

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "department")
class Department(
    @Id
    @Column(name = "department_id")
    val departmentId: Long,

    @Column(name = "name")
    val name: String,
) {
    override fun equals(other: Any?): Boolean = Objects.equals(departmentId, (other as? Department)?.departmentId)
    override fun hashCode(): Int = Objects.hashCode(departmentId)

    override fun toString(): String = "Department(departmentId=$departmentId)"
}

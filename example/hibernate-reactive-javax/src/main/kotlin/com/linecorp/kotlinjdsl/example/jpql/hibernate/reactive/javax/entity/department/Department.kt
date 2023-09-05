package com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.department

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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

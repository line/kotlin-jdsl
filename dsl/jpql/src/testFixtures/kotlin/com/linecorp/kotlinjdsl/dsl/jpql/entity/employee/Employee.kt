package com.linecorp.kotlinjdsl.dsl.jpql.entity.employee

abstract class Employee(
    val employeeId: Long,
    val name: String,
    val nickname: String?,
    val phone: String,
    val address: EmployeeAddress,
    val departments: MutableSet<EmployeeDepartment>,
) {
    fun getLowerName(): String = name.lowercase()
}

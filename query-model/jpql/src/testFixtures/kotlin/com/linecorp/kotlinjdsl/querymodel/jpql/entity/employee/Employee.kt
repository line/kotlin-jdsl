@file:Suppress("unused")

package com.linecorp.kotlinjdsl.querymodel.jpql.entity.employee

abstract class Employee(
    val employeeId: Long,
    val name: String,
    val nickname: String?,
    val phone: String,
    val address: EmployeeAddress,
    val departments: MutableSet<EmployeeDepartment>,
) {
    fun getUpperName(): String = name.uppercase()
}

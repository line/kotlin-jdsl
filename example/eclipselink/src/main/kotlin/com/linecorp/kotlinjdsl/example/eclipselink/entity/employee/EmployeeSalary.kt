@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.eclipselink.entity.employee

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class EmployeeSalary(
    @Column(name = "salary", scale = 2)
    val value: BigDecimal,
)

fun EmployeeSalary(int: Int): EmployeeSalary {
    return EmployeeSalary(BigDecimal.valueOf(int.toLong()).setScale(2))
}

fun EmployeeSalary(double: Double): EmployeeSalary {
    return EmployeeSalary(BigDecimal.valueOf(double).setScale(2))
}

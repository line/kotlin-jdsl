@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.eclipselink.javax.entity.employee

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

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

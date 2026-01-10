@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.employee

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class EmployeeSalary(
    @Column(name = "salary", scale = 2)
    val value: BigDecimal,
)

fun EmployeeSalary(int: Int): EmployeeSalary = EmployeeSalary(BigDecimal.valueOf(int.toLong()).setScale(2))

fun EmployeeSalary(double: Double): EmployeeSalary = EmployeeSalary(BigDecimal.valueOf(double).setScale(2))

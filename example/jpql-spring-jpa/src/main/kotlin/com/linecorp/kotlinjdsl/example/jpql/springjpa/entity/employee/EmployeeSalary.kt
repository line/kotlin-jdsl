package com.linecorp.kotlinjdsl.example.jpql.springjpa.entity.employee

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class EmployeeSalary(
    @Column(name = "salary")
    val value: BigDecimal,
)

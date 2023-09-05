package com.linecorp.kotlinjdsl.example.spring.batch.jpql.entity.employee

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class EmployeeAddress(
    @Column(name = "zip_code")
    val zipCode: String,

    @Column(name = "street_address_1")
    val streetAddress1: String,

    @Column(name = "street_address_2")
    val streetAddress2: String?,

    @Column(name = "city")
    val city: String,

    @Column(name = "province")
    val province: String,
)

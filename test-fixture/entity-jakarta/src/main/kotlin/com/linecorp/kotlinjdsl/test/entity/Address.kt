package com.linecorp.kotlinjdsl.test.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    @Column(name = "zip_code")
    val zipCode: String,

    @Column(name = "province")
    val province: String,

    @Column(name = "district")
    val district: String,

    @Column(name = "base_address")
    val baseAddress: String,

    @Column(name = "detail_address")
    val detailAddress: String,
)

data class AddressTestBuilder(
    var zipCode: String = "zipCode",
    var province: String = "province",
    var district: String = "district",
    var baseAddress: String = "baseAddress",
    var detailAddress: String = "detailAddress",
) {
    fun build() = Address(
        zipCode = zipCode,
        province = province,
        district = district,
        baseAddress = baseAddress,
        detailAddress = detailAddress,
    )
}

package com.linecorp.kotlinjdsl.test.entity.delivery

import com.linecorp.kotlinjdsl.test.entity.Address
import jakarta.persistence.*

@Entity
@Table(name = "test_delivery_address")
data class DeliveryAddress(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Embedded
    val address: Address
)

data class DeliveryAddressTestBuilder(
    var zipCode: String = "zipCode",
    var province: String = "province",
    var district: String = "district",
    var baseAddress: String = "baseAddress",
    var detailAddress: String = "detailAddress",
) {
    fun build() = DeliveryAddress(
        address = Address(
            zipCode = zipCode,
            province = province,
            district = district,
            baseAddress = baseAddress,
            detailAddress = detailAddress,
        )
    )
}

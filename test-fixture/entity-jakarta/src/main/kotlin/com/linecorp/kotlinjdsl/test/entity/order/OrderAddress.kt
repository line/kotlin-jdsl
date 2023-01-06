package com.linecorp.kotlinjdsl.test.entity.order

import com.linecorp.kotlinjdsl.test.entity.Address
import jakarta.persistence.*

@Entity
@Table(name = "test_order_address")
data class OrderAddress(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Embedded
    val address: Address
)

data class OrderAddressTestBuilder(
    var zipCode: String = "zipCode",
    var province: String = "province",
    var district: String = "district",
    var baseAddress: String = "baseAddress",
    var detailAddress: String = "detailAddress",
) {
    fun build() = OrderAddress(
        address = Address(
            zipCode = zipCode,
            province = province,
            district = district,
            baseAddress = baseAddress,
            detailAddress = detailAddress,
        ),
    )
}

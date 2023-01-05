package com.linecorp.kotlinjdsl.test.entity.delivery

import jakarta.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "test_delivery")
data class Delivery(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "order_id")
    val orderId: Long,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id")
    val address: DeliveryAddress,

    @OneToMany(mappedBy = "delivery", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val items: Set<DeliveryItem>,
) {
    init {
        @Suppress("LeakingThis")
        items.forEach { it.delivery = this }
    }
}

data class DeliveryTestBuilder(
    var orderId: Long = Random.nextLong(10_000_000, 99_999_999),
    var address: DeliveryAddress = DeliveryAddressTestBuilder().build(),
    var items: Set<DeliveryItem> = hashSetOf(DeliveryItemTestBuilder().build()),
) {
    fun build() = Delivery(
        orderId = orderId,
        address = address,
        items = items,
    )
}

package com.linecorp.kotlinjdsl.test.entity.delivery

import jakarta.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "test_delivery_item")
data class DeliveryItem(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "order_item_id")
    val orderItemId: Long,
) {
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    lateinit var delivery: Delivery
        internal set
}

data class DeliveryItemTestBuilder(
    var orderItemId: Long = Random.nextLong(10_000_000, 99_999_999),
) {
    fun build() = DeliveryItem(
        orderItemId = orderItemId,
    )
}

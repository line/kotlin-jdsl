package com.linecorp.kotlinjdsl.test.entity.order

import jakarta.persistence.*
import kotlin.random.Random

@Entity
@Table(
    name = "test_order",
    indexes = [Index(name = "idx1", columnList = "purchaser_id, id")]
)
data class Order(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "purchaser_id")
    val purchaserId: Long,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val groups: Set<OrderGroup>,
) {
    init {
        @Suppress("LeakingThis")
        groups.forEach { it.order = this }
    }
}

data class OrderTestBuilder(
    var purchaserId: Long = Random.nextLong(10_000_000, 99_999_999),
    var groups: Set<OrderGroup> = hashSetOf(OrderGroupTestBuilder().build())
) {
    fun build() = Order(
        purchaserId = purchaserId,
        groups = groups
    )
}

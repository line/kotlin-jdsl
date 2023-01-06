package com.linecorp.kotlinjdsl.test.entity.order

import java.math.BigDecimal
import jakarta.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "test_order_item")
class OrderItem(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "product_id")
    val productId: Long,

    @Column(name = "product_name")
    val productName: String,

    @Column(name = "product_image")
    val productImage: String?,

    @Column(name = "price")
    val price: BigDecimal,

    @Column(name = "quantity")
    val quantity: Long,

    @Column(name = "claimed")
    val claimed: Boolean,
) {
    @ManyToOne
    @JoinColumn(name = "group_id")
    lateinit var group: OrderGroup
        internal set
}

data class OrderItemTestBuilder(
    var productId: Long = Random.nextLong(10_000_000, 99_999_999),
    var productName: String = "productName${Random.nextInt(1, 10)}",
    var productImage: String? = null,
    var price: Int = Random.nextInt(1, 100),
    var quantity: Long = Random.nextLong(100, 999),
    var claimed: Boolean = false,
) {
    fun build() = OrderItem(
        productId = productId,
        productName = productName,
        productImage = productImage,
        price = price.toBigDecimal().setScale(2),
        quantity = quantity,
        claimed = claimed,
    )
}

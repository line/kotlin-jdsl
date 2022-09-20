package com.linecorp.kotlinjdsl.test.entity.order

import javax.persistence.*

@Entity
@Table(name = "test_order_group")
class OrderGroup(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val orderGroupName: String,

    @OneToMany(mappedBy = "group", cascade = [CascadeType.PERSIST], orphanRemoval = true)
    val items: Set<OrderItem>,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id")
    val address: OrderAddress
) {
    init {
        @Suppress("LeakingThis")
        items.forEach { it.group = this }
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    lateinit var order: Order
        internal set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderGroup) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}

data class OrderGroupTestBuilder(
    var items: Set<OrderItem> = hashSetOf(OrderItemTestBuilder().build()),
    var address: OrderAddress = OrderAddressTestBuilder().build(),
    var orderGroupName: String = "orderGroupName",
) {
    fun build() = OrderGroup(
        items = items,
        address = address,
        orderGroupName = orderGroupName,
    )
}

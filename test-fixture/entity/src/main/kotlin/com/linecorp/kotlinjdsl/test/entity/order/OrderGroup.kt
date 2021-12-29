package com.linecorp.kotlinjdsl.test.entity.order

import javax.persistence.*

@Entity
@Table(name = "test_order_group")
data class OrderGroup(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

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
}

data class OrderGroupTestBuilder(
    var items: Set<OrderItem> = hashSetOf(OrderItemTestBuilder().build()),
    var address: OrderAddress = OrderAddressTestBuilder().build()
) {
    fun build() = OrderGroup(
        items = items,
        address = address,
    )
}
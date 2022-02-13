package com.linecorp.kotlinjdsl.test.entity

import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryAddressTestBuilder
import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryItemTestBuilder
import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddressTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroupTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderItemTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderTestBuilder

abstract class EntityDsl {
    fun order(customize: OrderTestBuilder.() -> Unit) = OrderTestBuilder().apply(customize).build()
    fun orderGroup(customize: OrderGroupTestBuilder.() -> Unit) = OrderGroupTestBuilder().apply(customize).build()
    fun orderItem(customize: OrderItemTestBuilder.() -> Unit) = OrderItemTestBuilder().apply(customize).build()

    fun orderAddress(customize: OrderAddressTestBuilder.() -> Unit) = OrderAddressTestBuilder().apply(customize).build()

    fun delivery(customize: DeliveryTestBuilder.() -> Unit) = DeliveryTestBuilder().apply(customize).build()
    fun deliveryItem(customize: DeliveryItemTestBuilder.() -> Unit) = DeliveryItemTestBuilder().apply(customize).build()

    fun deliveryAddress(customize: DeliveryAddressTestBuilder.() -> Unit) =
        DeliveryAddressTestBuilder().apply(customize).build()
}

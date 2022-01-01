package com.linecorp.kotlinjdsl.test.entity

import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryAddressTestBuilder
import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryItemTestBuilder
import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddressTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroupTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderItemTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderTestBuilder
import javax.persistence.EntityManager

abstract class EntityDsl {
    fun EntityManager.persistAll(vararg entities: Any) = persistAll(entities.toList())
    fun EntityManager.persistAll(entities: Collection<Any>) = entities.forEach { persist(it) }
    fun EntityManager.removeAll(vararg entities: Any) = removeAll(entities.toList())
    fun EntityManager.removeAll(entities: Collection<Any>) = entities.forEach {
        if (contains(it)) {
            remove(it)
        } else {
            remove(merge(it))
        }
    }

    fun EntityManager.flushAndClear() {
        flush(); clear()
    }

    fun order(customize: OrderTestBuilder.() -> Unit) = OrderTestBuilder().apply(customize).build()
    fun orderGroup(customize: OrderGroupTestBuilder.() -> Unit) = OrderGroupTestBuilder().apply(customize).build()
    fun orderItem(customize: OrderItemTestBuilder.() -> Unit) = OrderItemTestBuilder().apply(customize).build()

    fun orderAddress(customize: OrderAddressTestBuilder.() -> Unit) = OrderAddressTestBuilder().apply(customize).build()

    fun delivery(customize: DeliveryTestBuilder.() -> Unit) = DeliveryTestBuilder().apply(customize).build()
    fun deliveryItem(customize: DeliveryItemTestBuilder.() -> Unit) = DeliveryItemTestBuilder().apply(customize).build()

    fun deliveryAddress(customize: DeliveryAddressTestBuilder.() -> Unit) =
        DeliveryAddressTestBuilder().apply(customize).build()
}

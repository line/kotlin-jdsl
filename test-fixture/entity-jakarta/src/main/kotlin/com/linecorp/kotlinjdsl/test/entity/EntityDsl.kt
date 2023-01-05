package com.linecorp.kotlinjdsl.test.entity

import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryAddressTestBuilder
import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryItemTestBuilder
import com.linecorp.kotlinjdsl.test.entity.delivery.DeliveryTestBuilder
import com.linecorp.kotlinjdsl.test.entity.employee.*
import com.linecorp.kotlinjdsl.test.entity.order.OrderAddressTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderGroupTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderItemTestBuilder
import com.linecorp.kotlinjdsl.test.entity.order.OrderTestBuilder

interface EntityDsl {
    fun order(customize: OrderTestBuilder.() -> Unit) = OrderTestBuilder().apply(customize).build()
    fun orderGroup(customize: OrderGroupTestBuilder.() -> Unit) = OrderGroupTestBuilder().apply(customize).build()
    fun orderItem(customize: OrderItemTestBuilder.() -> Unit) = OrderItemTestBuilder().apply(customize).build()

    fun orderAddress(customize: OrderAddressTestBuilder.() -> Unit) = OrderAddressTestBuilder().apply(customize).build()

    fun delivery(customize: DeliveryTestBuilder.() -> Unit) = DeliveryTestBuilder().apply(customize).build()
    fun deliveryItem(customize: DeliveryItemTestBuilder.() -> Unit) = DeliveryItemTestBuilder().apply(customize).build()

    fun deliveryAddress(customize: DeliveryAddressTestBuilder.() -> Unit) =
        DeliveryAddressTestBuilder().apply(customize).build()

    fun employee(customize: EmployeeTestBuilder.() -> Unit) = EmployeeTestBuilder().apply(customize).build()
    fun partTimeEmployee(customize: PartTimeEmployeeTestBuilder.() -> Unit) = PartTimeEmployeeTestBuilder().apply(customize).build()
    fun contractEmployee(customize: ContractEmployeeTestBuilder.() -> Unit) = ContractEmployeeTestBuilder().apply(customize).build()
    fun fullTimeEmployee(customize: FullTimeEmployeeTestBuilder.() -> Unit) = FullTimeEmployeeTestBuilder().apply(customize).build()
    fun project(customize: ProjectTestBuilder.() -> Unit) = ProjectTestBuilder().apply(customize).build()
}

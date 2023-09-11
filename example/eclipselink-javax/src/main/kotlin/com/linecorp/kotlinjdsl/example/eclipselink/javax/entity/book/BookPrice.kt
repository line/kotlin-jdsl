@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.eclipselink.javax.entity.book

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class BookPrice(
    @Column(name = "price", scale = 2)
    val value: BigDecimal,
)

fun BookPrice(int: Int): BookPrice {
    return BookPrice(BigDecimal.valueOf(int.toLong()).setScale(2))
}

fun BookPrice(double: Double): BookPrice {
    return BookPrice(BigDecimal.valueOf(double).setScale(2))
}

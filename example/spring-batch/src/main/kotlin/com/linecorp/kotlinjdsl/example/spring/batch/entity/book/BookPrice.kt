package com.linecorp.kotlinjdsl.example.spring.batch.entity.book

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

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

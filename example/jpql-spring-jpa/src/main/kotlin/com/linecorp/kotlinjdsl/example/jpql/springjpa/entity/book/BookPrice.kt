package com.linecorp.kotlinjdsl.example.jpql.springjpa.entity.book

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.math.BigDecimal

@Embeddable
data class BookPrice(
    @Column(name = "price")
    val value: BigDecimal,
)

fun BookPrice(int: Int): BookPrice {
    return BookPrice(BigDecimal.valueOf(int.toLong()))
}

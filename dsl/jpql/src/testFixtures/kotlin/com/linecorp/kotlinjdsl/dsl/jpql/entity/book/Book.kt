package com.linecorp.kotlinjdsl.dsl.jpql.entity.book

import java.math.BigDecimal
import java.time.OffsetDateTime

class Book(
    val isbn: Isbn,
    val title: String,
    val imageUrl: String,
    val price: BigDecimal,
    val salePrice: BigDecimal,
    val verticalLength: Int,
    val publishDate: OffsetDateTime,
    val authors: MutableSet<BookAuthor>,
    val publisher: BookPublisher,
)

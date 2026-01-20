package com.linecorp.kotlinjdsl.render.jpql.entity.book

import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.Calendar
import java.util.Date
import java.util.UUID

class Book(
    val isbn: Isbn,
    val title: String,
    val imageUrl: String,
    val status: BookStatus,
    val hasEbook: Boolean,
    val uuidField: UUID,
    val price: BigDecimal,
    val salePrice: BigDecimal,
    val publishDate: OffsetDateTime,
    val modifiedDate: Date,
    val authors: MutableSet<BookAuthor>,
    val publisher: BookPublisher,
    val firstLetter: Char,
    val publicationCalendar: Calendar,
    val coverImageBytes: ByteArray,
    val titleChars: CharArray,
)

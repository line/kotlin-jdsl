package com.linecorp.kotlinjdsl.support.spring.batch.entity.book

class BookAuthor(
    val book: Book,
    val authorId: Long,
    val authorType: BookAuthorType,
)

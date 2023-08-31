package com.linecorp.kotlinjdsl.dsl.jpql.entity.book

class BookAuthor(
    val book: Book,
    val authorId: Long,
    val authorType: BookAuthorType,
)

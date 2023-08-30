@file:Suppress("unused")

package com.linecorp.kotlinjdsl.querymodel.jpql.entity.book

class BookAuthor(
    val book: Book,
    val authorId: Long,
    val authorType: BookAuthorType,
)

package com.linecorp.kotlinjdsl.example.hibernate.reactive.javax.jpql.entity.book

import com.linecorp.kotlinjdsl.example.hibernate.reactive.javax.jpql.annotation.CompositeId
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "book_author")
@IdClass(BookAuthor.BookAuthorId::class)
class BookAuthor(
    @Id
    @Column(name = "author_id")
    val authorId: Long,
) {
    @Id
    @ManyToOne
    @JoinColumn(name = "isbn")
    lateinit var book: Book

    private val bookAuthorId get() = BookAuthorId(book.isbn, authorId)

    override fun equals(other: Any?): Boolean =
        Objects.equals(bookAuthorId, (other as? BookAuthor)?.bookAuthorId)

    override fun hashCode(): Int =
        Objects.hashCode(bookAuthorId)

    override fun toString(): String =
        "BookAuthor(bookAuthorId=$bookAuthorId)"

    @CompositeId
    data class BookAuthorId(
        val book: Isbn,
        val authorId: Long,
    ) : Serializable
}

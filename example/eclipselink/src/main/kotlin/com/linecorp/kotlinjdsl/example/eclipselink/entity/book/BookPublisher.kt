package com.linecorp.kotlinjdsl.example.eclipselink.entity.book

import com.linecorp.kotlinjdsl.example.eclipselink.annotation.CompositeId
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "book_publisher")
@IdClass(BookPublisher.BookPublisherId::class)
class BookPublisher(
    @Id
    @Column(name = "publisher_id")
    val publisherId: Long,
) {
    @Id
    @OneToOne
    @JoinColumn(name = "isbn")
    lateinit var book: Book

    private val bookPublisherId get() = BookPublisherId(book.isbn, publisherId)

    override fun equals(other: Any?): Boolean =
        Objects.equals(bookPublisherId, (other as? BookPublisher)?.bookPublisherId)

    override fun hashCode(): Int =
        Objects.hashCode(bookPublisherId)

    override fun toString(): String =
        "BookPublisher(bookPublisherId=$bookPublisherId)"

    @CompositeId
    data class BookPublisherId(
        val book: Isbn,
        val publisherId: Long,
    ) : Serializable
}

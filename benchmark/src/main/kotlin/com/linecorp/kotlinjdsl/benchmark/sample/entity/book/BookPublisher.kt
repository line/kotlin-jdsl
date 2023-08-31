package com.linecorp.kotlinjdsl.benchmark.sample.entity.book

import com.linecorp.kotlinjdsl.benchmark.sample.annotation.CompositeId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.io.Serializable
import java.util.*

@Table(name = "book_publisher")
@IdClass(BookPublisher.BookPublisherId::class)
@Entity
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

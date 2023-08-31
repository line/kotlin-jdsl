@file:Suppress("LeakingThis")

package com.linecorp.kotlinjdsl.example.eclipselink.entity.book

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "book")
class Book(
    @Id
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "isbn"))
    val isbn: Isbn,

    @Column(name = "title")
    var title: String,

    @Column(name = "image_url")
    var imageUrl: String,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "price"))
    var price: BookPrice,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "sale_price"))
    var salePrice: BookPrice,

    @Column(name = "publish_date")
    var publishDate: OffsetDateTime,

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    val authors: MutableSet<BookAuthor>,

    @OneToOne(mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    val publisher: BookPublisher,
) {
    init {
        authors.forEach { it.book = this }
        publisher.book = this
    }

    override fun equals(other: Any?): Boolean = Objects.equals(isbn, (other as? Book)?.isbn)
    override fun hashCode(): Int = Objects.hashCode(isbn)

    override fun toString(): String = "Book(isbn=$isbn)"
}

@file:Suppress("LeakingThis")

package com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax.entity.book

import java.time.OffsetDateTime
import java.util.*
import javax.persistence.AttributeOverride
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

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

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    val publishers: List<BookPublisher>,
) {
    init {
        authors.forEach { it.book = this }
        publishers.forEach { it.book = this }
    }

    override fun equals(other: Any?): Boolean = Objects.equals(isbn, (other as? Book)?.isbn)
    override fun hashCode(): Int = Objects.hashCode(isbn)

    override fun toString(): String = "Book(isbn=$isbn)"
}

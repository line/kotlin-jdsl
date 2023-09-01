package com.linecorp.kotlinjdsl.example.spring.batch.jpql.entity.author

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "author")
class Author(
    @Id
    @Column(name = "author_id")
    val authorId: Long,

    @Column(name = "name")
    var name: String,
) {
    override fun equals(other: Any?): Boolean = Objects.equals(authorId, (other as? Author)?.authorId)
    override fun hashCode(): Int = Objects.hashCode(authorId)

    override fun toString(): String = "Author(authorId=$authorId)"
}

@file:Suppress("JpaDataSourceORMInspection")

package com.linecorp.kotlinjdsl.example.eclipselink.javax.entity.author

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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

package com.linecorp.kotlinjdsl.example.spring.batch.entity.publisher

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "publisher")
class Publisher(
    @Id
    @Column(name = "publisher_id")
    val publisherId: Long,

    @Column(name = "name")
    var name: String,
) {
    override fun equals(other: Any?): Boolean = Objects.equals(publisherId, (other as? Publisher)?.publisherId)
    override fun hashCode(): Int = Objects.hashCode(publisherId)
}

@file:Suppress("JpaDataSourceORMInspection")

package com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.entity.publisher

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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

    override fun toString(): String = "Publisher(publisherId=$publisherId)"
}

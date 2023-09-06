package com.linecorp.kotlinjdsl.example.jpql.hibernate.javax.entity.book

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 * International Standard Book Number
 */
@Embeddable
data class Isbn(
    @Column(name = "isbn")
    val value: String,
) : Serializable

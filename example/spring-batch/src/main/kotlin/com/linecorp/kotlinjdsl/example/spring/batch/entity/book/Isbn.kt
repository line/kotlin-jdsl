package com.linecorp.kotlinjdsl.example.spring.batch.entity.book

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

/**
 * International Standard Book Number
 */
@Embeddable
data class Isbn(
    @Column(name = "isbn")
    val value: String,
) : Serializable


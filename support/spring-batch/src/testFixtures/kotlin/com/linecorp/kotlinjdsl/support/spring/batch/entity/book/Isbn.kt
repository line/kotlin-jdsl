package com.linecorp.kotlinjdsl.support.spring.batch.entity.book

import java.io.Serializable

/**
 * International Standard Book Number
 */
data class Isbn(
    val value: String,
) : Serializable

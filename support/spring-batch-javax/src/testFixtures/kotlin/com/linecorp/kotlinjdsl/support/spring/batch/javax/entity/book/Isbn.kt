package com.linecorp.kotlinjdsl.support.spring.batch.javax.entity.book

import java.io.Serializable

/**
 * International Standard Book Number
 */
data class Isbn(
    val value: String,
) : Serializable

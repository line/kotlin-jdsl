package com.linecorp.kotlinjdsl.render.jpql.entity.book

import java.io.Serializable

/**
 * International Standard Book Number
 */
data class Isbn(
    val value: String,
) : Serializable

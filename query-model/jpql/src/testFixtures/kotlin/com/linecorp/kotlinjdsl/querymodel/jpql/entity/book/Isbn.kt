package com.linecorp.kotlinjdsl.querymodel.jpql.entity.book

import java.io.Serializable

/**
 * International Standard Book Number
 */
data class Isbn(
    val value: String,
) : Serializable

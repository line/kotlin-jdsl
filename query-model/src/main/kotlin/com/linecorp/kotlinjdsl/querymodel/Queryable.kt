package com.linecorp.kotlinjdsl.querymodel

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Interface that can create [Query].
 *
 * Classes that inherit this can create new [Query], or return themselves by inheriting the [Query].
 */
@SinceJdsl("3.0.0")
interface Queryable<Q : Query<Q>> {
    /**
     * Creates a new [Query].
     */
    fun toQuery(): Q
}

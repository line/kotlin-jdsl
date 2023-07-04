package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * A class that can create or represent a [Query].
 *
 * Classes that inherit from this interface can create new [Query],
 * or return themselves by inheriting the [Query].
 */
@SinceJdsl("3.0.0")
interface Queryable<Q : Query<Q>> {
    /**
     * Returns a [Query] that represents the current state of the object.
     */
    fun toQuery(): Q
}

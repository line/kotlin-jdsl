package com.linecorp.kotlinjdsl.query

/**
 * A class that can create or represent a [Query].
 *
 * Classes that inherit from this interface can create new [Query], or return themselves by inheriting the [Query].
 */
interface Queryable<Q : Query<Q>> {
    /**
     * Returns a [Query] that represents the current state of the object.
     */
    fun toQuery(): Q
}

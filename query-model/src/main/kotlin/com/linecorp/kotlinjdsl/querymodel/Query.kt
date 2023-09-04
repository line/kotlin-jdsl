package com.linecorp.kotlinjdsl.querymodel

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Marker interface to represent a query.
 *
 * Classes that inherit this should be immutable.
 */
@SinceJdsl("3.0.0")
interface Query<SELF : Query<SELF>> : Queryable<SELF>, QueryPart {
    @Suppress("UNCHECKED_CAST")
    override fun toQuery(): SELF = this as SELF
}

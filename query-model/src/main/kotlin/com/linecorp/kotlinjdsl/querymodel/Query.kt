package com.linecorp.kotlinjdsl.querymodel

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Represents a query.
 *
 * Classes that inherit from this interface should be immutable.
 */
@SinceJdsl("3.0.0")
interface Query<SELF : Query<SELF>> : Queryable<SELF>, QueryPart {
    @Suppress("UNCHECKED_CAST")
    override fun toQuery(): SELF = this as SELF
}

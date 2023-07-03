package com.linecorp.kotlinjdsl.query

/**
 * Represents a query.
 *
 * Classes that inherit from this interface should be immutable.
 */
interface Query<SELF : Query<SELF>> : Queryable<SELF>, QueryPart {
    @Suppress("UNCHECKED_CAST")
    override fun toQuery(): SELF = this as SELF
}

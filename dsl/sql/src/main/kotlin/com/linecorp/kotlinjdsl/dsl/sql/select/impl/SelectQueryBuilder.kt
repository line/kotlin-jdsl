package com.linecorp.kotlinjdsl.dsl.sql.select.impl

import com.linecorp.kotlinjdsl.querymodel.sql.*
import com.linecorp.kotlinjdsl.querymodel.sql.impl.NormalSelectQuery

class SelectQueryBuilder(
    private val select: Collection<Expression<*>>,
) {
    private var distinct: Boolean = false
    private var from: MutableCollection<Table<*>>? = null
    private var where: Predicate? = null
    private var groupBy: MutableCollection<Expression<*>>? = null
    private var having: Predicate? = null
    private var orderBy: MutableCollection<Sort>? = null
    private var limit: Int? = null
    private var offset: Int? = null

    fun distinct(distinct: Boolean): SelectQueryBuilder {
        this.distinct = distinct

        return this
    }

    fun from(tables: Collection<Table<*>>): SelectQueryBuilder {
        this.from = (this.from ?: mutableListOf()).also { it.addAll(tables) }

        return this
    }

    fun where(predicate: Predicate): SelectQueryBuilder {
        where = predicate

        return this
    }

    fun groupBy(expressions: Collection<Expression<*>>): SelectQueryBuilder {
        this.groupBy = (this.groupBy ?: mutableListOf()).also { it.addAll(expressions) }

        return this
    }

    fun having(predicate: Predicate): SelectQueryBuilder {
        this.having = predicate

        return this
    }

    fun orderBy(sorts: Collection<Sort>): SelectQueryBuilder {
        this.orderBy = (this.orderBy ?: mutableListOf()).also { it.addAll(sorts) }

        return this
    }

    fun offset(offset: Int): SelectQueryBuilder {
        this.offset = offset

        return this
    }

    fun limit(limit: Int): SelectQueryBuilder {
        this.limit = limit

        return this
    }

    fun build(): SelectQuery {
        return NormalSelectQuery(
            select = select,
            distinct = distinct,
            from = from,
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
            offset = offset,
            limit = limit,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SelectQueryBuilder

        if (select != other.select) return false
        if (distinct != other.distinct) return false
        if (from != other.from) return false
        if (where != other.where) return false
        if (groupBy != other.groupBy) return false
        if (having != other.having) return false
        if (orderBy != other.orderBy) return false
        if (limit != other.limit) return false
        return offset == other.offset
    }

    override fun hashCode(): Int {
        var result = select.hashCode()
        result = 31 * result + distinct.hashCode()
        result = 31 * result + (from?.hashCode() ?: 0)
        result = 31 * result + (where?.hashCode() ?: 0)
        result = 31 * result + (groupBy?.hashCode() ?: 0)
        result = 31 * result + (having?.hashCode() ?: 0)
        result = 31 * result + (orderBy?.hashCode() ?: 0)
        result = 31 * result + (limit ?: 0)
        result = 31 * result + (offset ?: 0)
        return result
    }

    override fun toString(): String {
        return "SelectQueryBuilder(" +
            "select=$select, " +
            "distinct=$distinct, " +
            "from=$from, " +
            "where=$where, " +
            "groupBy=$groupBy, " +
            "having=$having, " +
            "orderBy=$orderBy, " +
            "limit=$limit, " +
            "offset=$offset)"
    }
}

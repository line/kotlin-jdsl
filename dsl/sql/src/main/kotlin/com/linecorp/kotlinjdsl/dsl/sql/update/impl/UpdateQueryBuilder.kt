package com.linecorp.kotlinjdsl.dsl.sql.update.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.TableReference
import com.linecorp.kotlinjdsl.querymodel.sql.UpdateQuery
import com.linecorp.kotlinjdsl.querymodel.sql.impl.NormalUpdateQuery

class UpdateQueryBuilder<T : Any>(
    private val table: TableReference<T>,
) {
    private var set: MutableMap<com.linecorp.kotlinjdsl.querymodel.sql.Column<T, *>, Expression<*>>? = null
    private var where: Predicate? = null
    private var orderBy: Iterable<Expression<*>>? = null

    fun set(
        column: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, *>,
        expression: Expression<*>
    ): UpdateQueryBuilder<T> {
        this.set = (this.set ?: mutableMapOf()).also { it[column] = expression }

        return this
    }

    fun where(predicate: Predicate): UpdateQueryBuilder<T> {
        this.where = predicate

        return this
    }

    fun build(): UpdateQuery<T> {
        val set = this.set ?: throw IllegalStateException("There is no set in UPDATE query.")

        return NormalUpdateQuery(
            table = table,
            sets = set,
            where = where,
            orderBy = orderBy,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateQueryBuilder<*>

        if (table != other.table) return false
        if (set != other.set) return false
        if (where != other.where) return false
        return orderBy == other.orderBy
    }

    override fun hashCode(): Int {
        var result = table.hashCode()
        result = 31 * result + (set?.hashCode() ?: 0)
        result = 31 * result + (where?.hashCode() ?: 0)
        result = 31 * result + (orderBy?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "UpdateQueryBuilder(" +
            "table=$table, " +
            "set=$set, " +
            "where=$where, " +
            "orderBy=$orderBy)"
    }
}

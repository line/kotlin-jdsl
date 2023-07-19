package com.linecorp.kotlinjdsl.dsl.sql.delete.impl

import com.linecorp.kotlinjdsl.querymodel.sql.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.TableReference
import com.linecorp.kotlinjdsl.querymodel.sql.impl.NormalDeleteQuery

class DeleteQueryBuilder<T : Any>(
    private val table: TableReference<T>,
) {
    private var where: Predicate? = null
    private var orderBy: Iterable<Expression<*>>? = null

    fun where(predicate: Predicate): DeleteQueryBuilder<T> {
        where = predicate

        return this
    }

    fun build(): DeleteQuery<T> {
        return NormalDeleteQuery(
            table = table,
            where = where,
            orderBy = orderBy,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeleteQueryBuilder<*>

        if (table != other.table) return false
        if (where != other.where) return false
        return orderBy == other.orderBy
    }

    override fun hashCode(): Int {
        var result = table.hashCode()
        result = 31 * result + (where?.hashCode() ?: 0)
        result = 31 * result + (orderBy?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "DeleteQueryBuilder(" +
            "table=$table, " +
            "where=$where, " +
            "orderBy=$orderBy)"
    }
}

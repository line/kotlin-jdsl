package com.linecorp.kotlinjdsl.dsl.jpql.update.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Queries
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

internal class UpdateQueryBuilder<T : Any>(
    private val entity: Path<T>,
    private val set: Collection<PathAndExpression<*>>
) {
    private var where: Predicate? = null

    fun where(predicate: Predicate): UpdateQueryBuilder<T> {
        where = predicate

        return this
    }

    fun build(): UpdateQuery<T> {
        return Queries.update(
            entity = entity,
            set = set,
            where = where,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateQueryBuilder<*>

        if (entity != other.entity) return false
        if (set != other.set) return false
        return where == other.where
    }

    override fun hashCode(): Int {
        var result = entity.hashCode()
        result = 31 * result + set.hashCode()
        result = 31 * result + (where?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "UpdateQueryBuilder(" +
            "entity=$entity, " +
            "set=$set, " +
            "where=$where)"
    }
}

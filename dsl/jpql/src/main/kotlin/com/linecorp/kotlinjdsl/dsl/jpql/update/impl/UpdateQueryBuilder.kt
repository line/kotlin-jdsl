package com.linecorp.kotlinjdsl.dsl.jpql.update.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

internal data class UpdateQueryBuilder<T : Any>(
    private val entity: Entity<T>,
    private val set: MutableMap<Path<*>, Expression<*>>,
    private var where: Predicate? = null,
) {
    constructor(
        entity: Entity<T>,
        path: Path<*>,
        value: Expression<*>,
    ) : this(
        entity = entity,
        set = mutableMapOf(path to value),
    )

    constructor(
        entity: Entity<T>,
        map: Map<Path<*>, Expression<*>>,
    ) : this(
        entity = entity,
        set = map.toMutableMap(),
    )

    fun <V : Any> set(path: Path<V>, value: Expression<V>): UpdateQueryBuilder<T> {
        set[path] = value

        return this
    }

    fun set(map: Map<Path<*>, Expression<*>>): UpdateQueryBuilder<T> {
        set.putAll(map)

        return this
    }

    fun where(predicate: Predicate): UpdateQueryBuilder<T> {
        where = predicate

        return this
    }

    fun build(): UpdateQuery<T> {
        return UpdateQueries.updateQuery(
            entity = entity,
            set = set,
            where = where,
        )
    }
}

package com.linecorp.kotlinjdsl.querymodel.jpql.entity

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntityTreat
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import kotlin.reflect.KClass

/**
 * Factory class that creates [Entity].
 */
@SinceJdsl("3.0.0")
object Entities {
    /**
     * Creates an entity expression with the type and alias.
     * The entity is identified and referenced by its alias.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Entity<T> {
        return JpqlEntity(type, alias)
    }

    /**
     * Creates an entity with downcasting.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> treat(entity: Entity<T>, type: KClass<S>): Entity<S> {
        return JpqlEntityTreat(entity, type)
    }

    /**
     * Creates a derived entity with the select query and alias.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> derivedEntity(
        selectQuery: SelectQuery<T>,
        alias: String = selectQuery.returnType.simpleName!!,
    ): Entity<T> {
        val trimmed = if (selectQuery is JpqlSelectQuery) {
            JpqlSelectQuery(
                returnType = selectQuery.returnType,
                select = selectQuery.select,
                distinct = selectQuery.distinct,
                from = selectQuery.from,
                where = selectQuery.where,
                groupBy = selectQuery.groupBy,
                having = selectQuery.having,
                orderBy = null,
            )
        } else {
            selectQuery
        }

        return JpqlDerivedEntity(
            selectQuery = trimmed,
            alias = alias,
        )
    }
}

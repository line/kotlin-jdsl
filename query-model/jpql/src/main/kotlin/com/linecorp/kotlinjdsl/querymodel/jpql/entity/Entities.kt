package com.linecorp.kotlinjdsl.querymodel.jpql.entity

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntityTreat
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import kotlin.reflect.KClass

@SinceJdsl("3.0.0")
object Entities {
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Entity<T> {
        return JpqlEntity(type, alias)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any, S : T> treat(entity: Entity<T>, type: KClass<S>): Entity<S> {
        return JpqlEntityTreat(entity, type)
    }

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

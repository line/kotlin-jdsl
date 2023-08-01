package com.linecorp.kotlinjdsl.querymodel.jpql.entity

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import kotlin.reflect.KClass

object Entities {
    @SinceJdsl("3.0.0")
    fun <T : Any> entity(type: KClass<T>, alias: String = type.simpleName!!): Entity<T> {
        return JpqlEntity(type, alias)
    }

    @SinceJdsl("3.0.0")
    fun <T : Any> derivedEntity(
        selectQuery: SelectQuery<T>,
        alias: String = selectQuery.returnType.simpleName!!,
    ): Entity<T> {
        return JpqlDerivedEntity(selectQuery, alias)
    }
}

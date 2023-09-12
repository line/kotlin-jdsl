package com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import kotlin.reflect.KClass

@Internal
data class JpqlDerivedEntity<T : Any>(
    val selectQuery: SelectQuery<T>,
    override val alias: String,
) : Entity<T> {
    val type: KClass<T> = selectQuery.returnType
}

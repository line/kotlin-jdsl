package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.*

data class JpqlSelectQuery(
    val select: Collection<Expression<*>>,
    val distinct: Boolean,
    val from: Collection<Path<*>>,
    val where: Predicate?,
    val groupBy: Collection<Expression<*>>?,
    val having: Predicate?,
    val orderBy: Collection<Sort>?,
) : SelectQuery

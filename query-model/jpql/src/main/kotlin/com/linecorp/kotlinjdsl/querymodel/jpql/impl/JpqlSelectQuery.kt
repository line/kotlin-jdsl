package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.*
import kotlin.reflect.KClass

data class JpqlSelectQuery<T>(
    val returnType: KClass<*>,
    val select: Collection<Expression<*>>,
    val distinct: Boolean,
    val from: Collection<Path<*>>,
    val where: Predicate?,
    val groupBy: Collection<Expression<*>>?,
    val having: Predicate?,
    val orderBy: Collection<Sort>?,
) : SelectQuery<T>

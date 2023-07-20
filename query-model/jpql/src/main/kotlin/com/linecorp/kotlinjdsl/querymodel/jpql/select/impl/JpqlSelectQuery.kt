package com.linecorp.kotlinjdsl.querymodel.jpql.select.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

@Internal
data class JpqlSelectQuery<T> internal constructor(
    override val returnType: KClass<*>,
    val select: Iterable<Expression<*>>,
    val distinct: Boolean,
    val from: Iterable<Path<*>>,
    val where: Predicate?,
    val groupBy: Iterable<Expression<*>>?,
    val having: Predicate?,
    val orderBy: Iterable<Sort>?,
) : SelectQuery<T>

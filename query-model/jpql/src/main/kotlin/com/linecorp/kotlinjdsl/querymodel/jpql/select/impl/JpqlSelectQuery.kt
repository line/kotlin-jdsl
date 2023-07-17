package com.linecorp.kotlinjdsl.querymodel.jpql.select.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

data class JpqlSelectQuery<T> internal constructor(
    override val returnType: KClass<*>,
    val select: Collection<Expression<*>>,
    val distinct: Boolean,
    val from: Collection<Path<*>>,
    val where: Predicate?,
    val groupBy: Collection<Expression<*>>?,
    val having: Predicate?,
    val orderBy: Collection<Sort>?,
) : SelectQuery<T>

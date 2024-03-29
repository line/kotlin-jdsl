package com.linecorp.kotlinjdsl.querymodel.jpql.select.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

@Internal
data class JpqlSelectQuery<T : Any> internal constructor(
    override val returnType: KClass<T>,
    val distinct: Boolean,
    val select: Iterable<Expression<*>>,
    val from: Iterable<From>,
    val where: Predicate?,
    val groupBy: Iterable<Expression<*>>?,
    val having: Predicate?,
    val orderBy: Iterable<Sort>?,
) : SelectQuery<T> {
    init {
        require(select.any()) {
            "There is no select in a select query. The expression list in the select clause may be emptyList."
        }

        require(from.any()) {
            "There is no from in a select query. The from list in the from clause may be emptyList."
        }
    }
}

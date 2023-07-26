package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

object Selects {
    @SinceJdsl("3.0.0")
    fun <T : Any> select(
        returnType: KClass<*>,
        distinct: Boolean,
        select: Iterable<Expressionable<*>>,
        from: Iterable<From>,
        where: Predicate?,
        groupBy: Iterable<Expressionable<*>>?,
        having: Predicate?,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQuery(
            returnType = returnType,
            distinct = distinct,
            select = select.map { it.toExpression() },
            from = from,
            where = where,
            groupBy = groupBy?.map { it.toExpression() },
            having = having,
            orderBy = orderBy,
        )
    }
}

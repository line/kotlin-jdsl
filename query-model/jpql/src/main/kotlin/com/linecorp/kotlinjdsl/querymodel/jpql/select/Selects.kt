package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Froms
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

object Selects {
    @SinceJdsl("3.0.0")
    fun <T : Any> select(
        returnType: KClass<T>,
        distinct: Boolean,
        select: Iterable<Expression<*>>,
        from: Iterable<From>,
        where: Predicate?,
        groupBy: Iterable<Expression<*>>?,
        having: Predicate?,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQuery(
            returnType = returnType,
            distinct = distinct,
            select = select,
            from = Froms.reduce(from),
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
        )
    }
}

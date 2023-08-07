package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.from.impl.JpqlJoinedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
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
            from = reduce(from),
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
        )
    }

    private fun reduce(froms: Iterable<From>): Iterable<From> {
        val reduced = mutableListOf<From>()

        for ((index, from) in froms.withIndex()) {
            if (index == 0) {
                reduced.add(from)
                continue
            }

            val last = reduced.last()

            if (last is Entity<*> && from is Join) {
                reduced[reduced.lastIndex] = JpqlJoinedEntity(last, from)
            } else {
                reduced.add(from)
            }
        }

        return reduced
    }
}

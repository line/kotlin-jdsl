package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.impl.JpqlUpdateQuery
import kotlin.reflect.KClass

object Queries {
    fun <T> select(
        returnType: KClass<*>,
        select: Iterable<Expression<*>>,
        distinct: Boolean,
        from: Iterable<Path<*>>,
        where: Predicate?,
        groupBy: Iterable<Expression<*>>?,
        having: Predicate?,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQuery(
            returnType = returnType,
            select = select,
            distinct = distinct,
            from = from.map { Paths.alias(it) },
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
        )
    }

    fun <T : Any> update(
        entity: Path<T>,
        set: Iterable<PathAndExpression<*>>,
        where: Predicate?,
    ): UpdateQuery<T> {
        return JpqlUpdateQuery(
            entity = Paths.alias(entity),
            set = set.associate { it.first to it.second },
            where = where,
        )
    }

    fun <T : Any> delete(
        from: Path<T>,
        where: Predicate?,
    ): DeleteQuery<T> {
        return JpqlDeleteQuery(
            from = from,
            where = where,
        )
    }
}

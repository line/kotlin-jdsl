package com.linecorp.kotlinjdsl.dsl.jpql.update.impl

import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery

@PublishedApi
internal data class UpdateQueryDsl<T : Any>(
    private val builder: UpdateQueryBuilder<T>
) : UpdateQuerySetFirstStep<T>,
    UpdateQuerySetStep<T>,
    UpdateQueryWhereStep<T> {
    constructor(entity: Entity<T>, path: Path<*>, value: Expression<*>) : this(UpdateQueryBuilder(entity, path, value))
    constructor(entity: Entity<T>, map: Map<Path<*>, Expression<*>>) : this(UpdateQueryBuilder(entity, map))

    override fun <V : Any, S : V?> set(path: Path<V>, value: S): UpdateQuerySetStep<T> {
        builder.set(path, Expressions.value(value))

        return this
    }

    override fun <V : Any> set(path: Path<V>, value: Expressionable<V>): UpdateQuerySetStep<T> {
        builder.set(path, value.toExpression())

        return this
    }

    override fun where(predicate: Predicatable?): JpqlQueryable<UpdateQuery<T>> {
        if (predicate != null) {
            builder.where(predicate.toPredicate())
        }

        return this
    }

    override fun whereAnd(vararg predicates: Predicatable?): JpqlQueryable<UpdateQuery<T>> {
        builder.where(Predicates.and(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun whereOr(vararg predicates: Predicatable?): JpqlQueryable<UpdateQuery<T>> {
        builder.where(Predicates.or(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun toQuery(): UpdateQuery<T> {
        return builder.build()
    }
}

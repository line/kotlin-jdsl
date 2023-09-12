package com.linecorp.kotlinjdsl.dsl.jpql.update.impl

import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetStep
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Pathable

@PublishedApi
internal data class UpdateQuerySetStepFirstDsl<T : Any>(
    private val entity: Entity<T>,
) : UpdateQuerySetFirstStep<T> {
    override fun <V : Any, S : V?> set(path: Pathable<V>, value: S): UpdateQuerySetStep<T> {
        return UpdateQueryDsl(entity, path.toPath(), Expressions.value(value))
    }

    override fun <V : Any> set(path: Pathable<V>, value: Expressionable<V>): UpdateQuerySetStep<T> {
        return UpdateQueryDsl(entity, path.toPath(), value.toExpression())
    }
}

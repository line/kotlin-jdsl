package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.dsl.jpql.expression.PathAndExpression

interface UpdateQuerySetStep<T : Any> {
    fun set(vararg assignments: PathAndExpression<*>): UpdateQueryWhereStep<T>

    fun set(assignments: Collection<PathAndExpression<*>>): UpdateQueryWhereStep<T>
}

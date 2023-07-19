package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression

interface UpdateQuerySetStep<T : Any> {
    fun set(vararg assignments: PathAndExpression<*>): UpdateQueryWhereStep<T>

    fun set(assignments: Iterable<PathAndExpression<*>>): UpdateQueryWhereStep<T>
}

package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.Path

interface SelectQueryFromStep<T> {
    fun from(vararg paths: Path<*>): SelectQueryWhereStep<T>

    fun from(paths: Collection<Path<*>>): SelectQueryWhereStep<T>
}

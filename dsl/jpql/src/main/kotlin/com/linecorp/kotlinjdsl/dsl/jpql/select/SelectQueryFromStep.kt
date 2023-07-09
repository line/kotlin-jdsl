package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.Path

interface SelectQueryFromStep {
    fun from(vararg paths: Path<*>): SelectQueryWhereStep

    fun from(paths: Collection<Path<*>>): SelectQueryWhereStep
}

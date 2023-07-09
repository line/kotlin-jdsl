package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.SelectQuery

interface SelectQueryGroupByStep : SelectQueryHavingStep, JpqlQueryable<SelectQuery> {
    fun groupBy(vararg expressions: Expressionable<*>): SelectQueryHavingStep

    fun groupBy(expressions: Collection<Expressionable<*>>): SelectQueryHavingStep
}

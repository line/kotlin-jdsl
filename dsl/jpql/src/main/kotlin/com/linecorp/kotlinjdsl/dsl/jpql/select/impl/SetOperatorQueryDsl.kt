package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryOrderByStep
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable
import kotlin.reflect.KClass

@PublishedApi
internal class SetOperatorQueryDsl<T : Any>(
    private val builder: SetOperatorSelectQueryBuilder<T>,
) : SelectQueryOrderByStep<T>, JpqlQueryable<SelectQuery<T>> {
    constructor(
        returnType: KClass<T>,
        leftQuery: JpqlQueryable<SelectQuery<T>>,
        rightQuery: JpqlQueryable<SelectQuery<T>>,
        setOperator: SetOperator,
    ) : this(
        SetOperatorSelectQueryBuilder<T>(returnType, leftQuery, rightQuery, setOperator),
    )

    override fun orderBy(vararg sorts: Sortable?): JpqlQueryable<SelectQuery<T>> {
        builder.orderBy(sorts.mapNotNull { it?.toSort() })
        return this
    }

    override fun toQuery(): SelectQuery<T> {
        return builder.build()
    }
}

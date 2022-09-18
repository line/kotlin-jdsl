package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*
import kotlin.reflect.KProperty1

data class ColumnSpec<T>(
    val entity: EntitySpec<*>,
    val path: String
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return path(froms)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return path(froms)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return path(froms)
    }

    fun path(froms: Froms): Path<T> =
        froms[entity].get(path)

    fun <NT> nested(property: KProperty1<T, NT>): NestedColumnSpec<NT> = NestedColumnSpec(
        this,
        property.name
    )
}

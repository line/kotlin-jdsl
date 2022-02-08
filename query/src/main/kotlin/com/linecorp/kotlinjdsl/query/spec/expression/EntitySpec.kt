package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression

data class EntitySpec<T>(
    val type: Class<T>,
    val alias: String = "$DEFAULT_ALIAS_TOKEN${type.name}"
) : ExpressionSpec<T> {
    companion object {
        const val DEFAULT_ALIAS_TOKEN = "\\"
    }
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = froms[this]

    fun criteriaAlias() = alias.takeIf { !it.startsWith(DEFAULT_ALIAS_TOKEN) }
}

package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*

data class EntitySpec<T>(
    val type: Class<T>,
    val alias: String = "$DEFAULT_ALIAS_TOKEN${type.name}"
) : ExpressionSpec<T> {
    companion object {
        private const val DEFAULT_ALIAS_TOKEN = "\\"
    }
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = froms[this].apply { applyAlias() }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = froms[this].apply { applyAlias() }

    private fun Path<T>.applyAlias() {
        this@EntitySpec.alias.takeIf { !it.startsWith(DEFAULT_ALIAS_TOKEN) }?.run { alias(this) }
    }
}

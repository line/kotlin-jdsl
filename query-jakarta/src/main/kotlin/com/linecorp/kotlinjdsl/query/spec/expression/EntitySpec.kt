package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.*

data class EntitySpec<T>(
    val type: Class<T>,
    private val alias: String? = null
) : ExpressionSpec<T> {
    companion object {
        private const val DEFAULT_ALIAS_TOKEN = "\\"
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = path(froms)

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = path(froms)

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = path(froms)

    private fun path(froms: Froms) = froms[this].apply { applyAlias() }

    private fun Path<T>.applyAlias() {
        this@EntitySpec.alias?.run { alias(this) }
    }
}

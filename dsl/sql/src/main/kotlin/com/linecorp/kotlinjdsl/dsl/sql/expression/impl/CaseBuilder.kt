package com.linecorp.kotlinjdsl.dsl.sql.expression.impl

import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Predicate
import com.linecorp.kotlinjdsl.query.sql.impl.Case

class CaseBuilder<T> {
    private var whens: MutableList<Case.When<T>>? = null
    private var `else`: Expression<out T>? = null

    fun `when`(predicate: Predicate, then: Expression<T>): CaseBuilder<T> {
        this.whens = (this.whens ?: mutableListOf()).also { it.add(Case.When(predicate, then)) }

        return this
    }

    fun `else`(expression: Expression<out T>): CaseBuilder<T> {
        this.`else` = expression

        return this
    }

    fun build(): Case<T> {
        val whens = this.whens ?: throw IllegalStateException("There is no when in CASE Clause.")

        return Case(
            whens = whens,
            `else` = `else`,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CaseBuilder<*>

        if (whens != other.whens) return false
        return `else` == other.`else`
    }

    override fun hashCode(): Int {
        var result = whens?.hashCode() ?: 0
        result = 31 * result + (`else`?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "CaseBuilder(" +
            "whens=$whens, " +
            "`else`=$`else`)"
    }
}

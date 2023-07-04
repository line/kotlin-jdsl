package com.linecorp.kotlinjdsl.dsl.sql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate
import com.linecorp.kotlinjdsl.querymodel.sql.impl.Case
import com.linecorp.kotlinjdsl.querymodel.sql.impl.CaseWhen

class CaseBuilder<T> {
    private var whens: MutableList<CaseWhen<T>>? = null
    private var `else`: Expression<out T>? = null

    fun `when`(predicate: Predicate, then: Expression<T>): CaseBuilder<T> {
        this.whens = (this.whens ?: mutableListOf()).also { it.add(CaseWhen(predicate, then)) }

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

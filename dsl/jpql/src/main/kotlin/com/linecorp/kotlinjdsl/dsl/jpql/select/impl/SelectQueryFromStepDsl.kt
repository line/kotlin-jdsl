package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import kotlin.reflect.KClass

internal class SelectQueryFromStepDsl<T>(
    private val returnType: KClass<*>,
    private val select: Collection<Expression<*>>,
    private val distinct: Boolean,
) : SelectQueryFromStep<T> {
    override fun from(vararg paths: Path<*>): SelectQueryWhereStep<T> {
        return SelectQueryDsl(returnType, select, distinct, paths.toList())
    }

    override fun from(paths: Collection<Path<*>>): SelectQueryWhereStep<T> {
        return SelectQueryDsl(returnType, select, distinct, paths)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SelectQueryFromStepDsl<*>

        if (select != other.select) return false
        return distinct == other.distinct
    }

    override fun hashCode(): Int {
        var result = select.hashCode()
        result = 31 * result + distinct.hashCode()
        return result
    }

    override fun toString(): String {
        return "SelectQueryFromStepDsl(" +
            "select=$select, " +
            "distinct=$distinct)"
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.update.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.PathAndExpression
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.Path

internal class UpdateQuerySetStepDsl<T : Any>(
    private val entity: Path<T>,
) : UpdateQuerySetStep<T> {
    override fun set(vararg assignments: PathAndExpression<*>): UpdateQueryWhereStep<T> {
        return UpdateQueryDsl(
            entity = entity,
            set = assignments.associate { it.first to it.second },
        )
    }

    override fun set(assignments: Collection<PathAndExpression<*>>): UpdateQueryWhereStep<T> {
        return UpdateQueryDsl(
            entity = entity,
            set = assignments.associate { it.first to it.second },
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateQuerySetStepDsl<*>

        return entity == other.entity
    }

    override fun hashCode(): Int {
        return entity.hashCode()
    }

    override fun toString(): String {
        return "UpdateQuerySetStepDsl(" +
            "entity=$entity)"
    }
}

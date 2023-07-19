package com.linecorp.kotlinjdsl.dsl.jpql.update.impl

import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQuerySetStep
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression

internal class UpdateQuerySetStepDsl<T : Any>(
    private val entity: Path<T>,
) : UpdateQuerySetStep<T> {
    override fun set(vararg assignments: PathAndExpression<*>): UpdateQueryWhereStep<T> {
        return UpdateQueryDsl(
            entity = entity,
            set = assignments.toList(),
        )
    }

    override fun set(assignments: Iterable<PathAndExpression<*>>): UpdateQueryWhereStep<T> {
        return UpdateQueryDsl(
            entity = entity,
            set = assignments,
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

package com.linecorp.kotlinjdsl.query.spec

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root

class Froms internal constructor(
    val root: Root<*>,
    private val map: Map<EntitySpec<*>, Path<*>>
) {
    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: EntitySpec<T>): Path<T> =
        map[key] as? Path<T>
            ?: throw IllegalStateException("There is no $key in from or join clause. contains: ${map.keys}")

    operator fun plus(other: Froms): Froms {
        val duplicatedEntities = map.keys.intersect(other.map.keys)

        if (duplicatedEntities.isNotEmpty()) {
            throw IllegalStateException(
                "Other froms has duplicated entitySpec. Please alias the duplicated entities: $duplicatedEntities"
            )
        }

        return Froms(root, map + other.map)
    }
}

package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.SimpleAssociatedJoinSpec
import com.linecorp.kotlinjdsl.query.spec.AssociatedJoinSpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.JoinSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import java.util.*
import javax.persistence.criteria.Path
import javax.persistence.criteria.Root

class SimpleAssociator(
    fromEntity: EntitySpec<*>,
    joins: Collection<JoinSpec<*>>,
    private val root: Root<*>
) {
    private val realized: LinkedHashMap<EntitySpec<*>, Path<*>> = linkedMapOf(fromEntity to root)
    private val realizedListeners: LinkedHashMap<EntitySpec<*>, LinkedList<() -> Unit>> = linkedMapOf()

    init {
        joins.asSequence()
            .filter { !realized.contains(it.entity) }
            .onEach { if (it !is SimpleAssociatedJoinSpec<*, *>) throw IllegalArgumentException("it allows only ${SimpleAssociatedJoinSpec::class.simpleName} type") }
            .forEach {
                it as SimpleAssociatedJoinSpec<*, *>
                if (!realized.contains(it.left)) {
                    realizeLazy(it)
                } else {
                    realize(it)
                }
            }
    }

    private fun realizeLazy(spec: AssociatedJoinSpec<*, *>) {
        realizedListeners.computeIfAbsent(spec.left) { LinkedList() }.add {
            if (!realized.contains(spec.right)) {
                realize(spec)
            }
        }
    }

    private fun realize(spec: AssociatedJoinSpec<*, *>) {
        realize(spec.right) { associate(spec) }
    }

    private fun realize(entity: EntitySpec<*>, associate: () -> Path<*>) {
        realized[entity] = associate()
        realizedListeners.remove(entity)?.run {
            this.forEach { it() }
        }
    }

    private fun associate(spec: AssociatedJoinSpec<*, *>): Path<*> =
        realized.getValue(spec.left).get<Any>(spec.path)

    fun associateAll(): Froms {
        if (realizedListeners.isNotEmpty()) {
            throw IllegalStateException(
                "Associate clause is incomplete. Please check if the following Entities are associated. " +
                    "Entities: ${realizedListeners.keys}"
            )
        }

        return Froms(
            root = root,
            map = realized
        )
    }
}

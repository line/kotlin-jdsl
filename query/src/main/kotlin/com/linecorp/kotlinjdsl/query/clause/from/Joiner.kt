package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.*
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import java.util.*
import javax.persistence.criteria.*

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
class Joiner(
    fromEntity: EntitySpec<*>,
    joins: Collection<JoinSpec<*>>,
    private val query: AbstractQuery<*>
) {
    private val root: Root<*> = query.from(fromEntity.type)

    private val realized: LinkedHashMap<EntitySpec<*>, From<*, *>> = linkedMapOf(fromEntity to root)
    private val realizedListeners: LinkedHashMap<EntitySpec<*>, LinkedList<() -> Unit>> = linkedMapOf()

    init {
        joins.asSequence()
            .sortedWith(joinSpecComparator)
            .filter { !realized.contains(it.entity) }
            .forEach {
                when (it) {
                    is CrossJoinSpec<*> -> realize(it)
                    is AssociatedJoinSpec<*, *> -> {
                        if (!realized.contains(it.left)) {
                            realizeLazy(it)
                        } else {
                            realize(it)
                        }
                    }
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
        realize(spec.right) { join(spec) }
    }

    private fun realize(spec: CrossJoinSpec<*>) {
        realize(spec.entity) { query.from(spec.entity.type) }
    }

    private fun realize(entity: EntitySpec<*>, join: () -> From<*, *>) {
        realized[entity] = join()
        realizedListeners.remove(entity)?.run {
            this.forEach { it() }
        }
    }

    private fun join(spec: AssociatedJoinSpec<*, *>): From<*, *> =
        when (spec) {
            is SimpleJoinSpec, is SimpleAssociatedJoinSpec ->
                realized.getValue(spec.left).join<Any, Any>(spec.path, spec.joinType)
            is FetchJoinSpec ->
                realized.getValue(spec.left).fetch(spec.path, spec.joinType)
        } as From<*, *>

    fun joinAll(): Froms {
        if (realizedListeners.isNotEmpty()) {
            throw IllegalStateException(
                "Join clause is incomplete. Please check if the following Entities are joined. " +
                    "Entities: ${realizedListeners.keys}"
            )
        }

        return Froms(
            root = root,
            map = realized
        )
    }

    companion object {
        private val fetchJoinFirstComparator: Comparator<JoinSpec<*>> = Comparator { o1, o2 ->
            when {
                o1::class == o2::class -> 0
                o1 is FetchJoinSpec<*, *> -> -1
                else -> 1
            }
        }

        private val outerJoinFirstComparator: Comparator<JoinSpec<*>> = Comparator { o1, o2 ->
            when {
                o1 is CrossJoinSpec<*> -> -1
                o2 is CrossJoinSpec<*> -> 1
                o1 is AssociatedJoinSpec<*, *> && o1.joinType.isOuterJoin() -> -1
                o2 is AssociatedJoinSpec<*, *> && o2.joinType.isOuterJoin() -> 1
                else -> 0
            }
        }

        private val joinSpecComparator = fetchJoinFirstComparator.thenComparing(outerJoinFirstComparator)

        private fun JoinType.isOuterJoin(): Boolean {
            return this === JoinType.LEFT || this === JoinType.RIGHT
        }
    }
}

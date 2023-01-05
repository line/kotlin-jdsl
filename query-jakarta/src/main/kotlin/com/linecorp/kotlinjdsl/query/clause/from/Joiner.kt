@file:Suppress("UNCHECKED_CAST")

package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.*
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import java.util.*
import jakarta.persistence.criteria.*

// In the case of Treat, Type should be clearly treated as a parent/child relationship,
// but it was used because it needed a parent/child relationship that can be cast forcibly due to the current structure that receives JoinSpec as *.
// Since it is used only internally, it is declared as a private alias.
private typealias ExplicitErasedParent = Number
private typealias ExplicitErasedChild = Int

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
class Joiner(
    fromEntity: EntitySpec<*>,
    joins: Collection<JoinSpec<*>>,
    private val query: AbstractQuery<*>,
    private val criteriaBuilder: CriteriaBuilder
) {
    private val root: Root<*> = query.from(fromEntity.type)

    private val realized: LinkedHashMap<EntitySpec<*>, From<*, *>> = linkedMapOf(fromEntity to root)
    private val realizedListeners: LinkedHashMap<EntitySpec<*>, LinkedList<() -> Unit>> = linkedMapOf()

    init {
        joins.asSequence()
            .sortedWith(joinSpecComparator)
            .filter { !realized.contains(it.entity) }
            .forEach { spec ->
                when (spec) {
                    is CrossJoinSpec<*> -> realize(spec)
                    is TreatJoinSpec<*, *> -> treat(spec)
                    is AssociatedJoinSpec<*, *> -> {
                        if (!realized.contains(spec.left)) {
                            realizeLazy(spec)
                        } else {
                            realize(spec)
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
                realized.getValue(spec.left).join(spec.path, spec.joinType)
            is FetchJoinSpec ->
                realized.getValue(spec.left).fetch<Any, Any>(spec.path, spec.joinType)
            else -> throw IllegalArgumentException()
        } as From<*, *>

    private fun treat(spec: TreatJoinSpec<*, *>) {
        realized[spec.right] = when (spec.root.entity == spec.left && spec.root.entity.type == root.javaType) {
            true -> criteriaBuilder.treat(
                root as Root<ExplicitErasedParent>,
                spec.right.type as Class<ExplicitErasedChild>
            )
            false -> {
                val join = realized.computeIfAbsent(spec.left) {
                    realized.getValue(spec.root.entity)
                        .join<ExplicitErasedParent, ExplicitErasedChild>(spec.path, spec.joinType)
                } as Join<ExplicitErasedParent, ExplicitErasedChild>

                when (join) {
                    is ListJoin<ExplicitErasedParent, ExplicitErasedChild> -> criteriaBuilder.treat(
                        join,
                        spec.right.type as Class<ExplicitErasedChild>
                    )
                    is CollectionJoin<ExplicitErasedParent, ExplicitErasedChild> -> criteriaBuilder.treat(
                        join,
                        spec.right.type as Class<ExplicitErasedChild>
                    )
                    is SetJoin<ExplicitErasedParent, ExplicitErasedChild> -> criteriaBuilder.treat(
                        join,
                        spec.right.type as Class<ExplicitErasedChild>
                    )
                    is MapJoin<ExplicitErasedParent, *, ExplicitErasedChild> -> criteriaBuilder.treat(
                        join,
                        spec.right.type as Class<ExplicitErasedChild>
                    )
                    else -> criteriaBuilder.treat(
                        join,
                        spec.right.type as Class<ExplicitErasedChild>
                    )
                }
            }
        } as From<*, *>
    }

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
                o1 is TreatJoinSpec<*, *> && o2 !is TreatJoinSpec<*, *> -> 1
                o1 !is TreatJoinSpec<*, *> && o2 is TreatJoinSpec<*, *> -> -1
                o1 is CrossJoinSpec<*> -> -1
                o2 is CrossJoinSpec<*> -> 1
                o1 is AssociatedJoinSpec<*, *> && o1.joinType.isOuterJoin() -> -1
                o2 is AssociatedJoinSpec<*, *> && o2.joinType.isOuterJoin() -> 1
                else -> 0
            }
        }

        private val treatJoinOuterFirstComparator = Comparator<JoinSpec<*>> { o1, o2 ->
            when {
                o1 is TreatJoinSpec<* ,*> && o1.joinType.isOuterJoin() -> -1
                o2 is TreatJoinSpec<* ,*> && o2.joinType.isOuterJoin() -> -1
                else -> 0
            }
        }

        private val joinSpecComparator =
            fetchJoinFirstComparator.thenComparing(outerJoinFirstComparator).thenComparing(treatJoinOuterFirstComparator)

        private fun JoinType.isOuterJoin(): Boolean {
            return this === JoinType.LEFT || this === JoinType.RIGHT
        }
    }
}

package com.linecorp.kotlinjdsl.render

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
abstract class AbstractRenderContextElement(
    override val key: RenderContext.Key<*>,
) : RenderContext.Element

@SinceJdsl("3.0.0")
data object EmptyRenderContext : RenderContext {
    override fun <E : RenderContext.Element> get(key: RenderContext.Key<E>): E? = null
    override fun <R> fold(initial: R, operation: (R, RenderContext.Element) -> R): R = initial
    override fun plus(context: RenderContext): RenderContext = context
    override fun minusKey(key: RenderContext.Key<*>): RenderContext = this
}

internal class CombinedRenderContext(
    private val left: RenderContext,
    private val element: RenderContext.Element,
) : RenderContext {
    override fun <E : RenderContext.Element> get(key: RenderContext.Key<E>): E? {
        var cur = this

        while (true) {
            cur.element[key]?.let {
                return it
            }

            val next = cur.left

            if (next is CombinedRenderContext) {
                cur = next
            } else {
                return next[key]
            }
        }
    }

    override fun <R> fold(initial: R, operation: (R, RenderContext.Element) -> R): R {
        return operation(left.fold(initial, operation), element)
    }

    override fun minusKey(key: RenderContext.Key<*>): RenderContext {
        if (element[key] != null) {
            return left
        }

        val newLeft = left.minusKey(key)

        return when {
            newLeft === left -> this
            newLeft === EmptyRenderContext -> element
            else -> CombinedRenderContext(newLeft, element)
        }
    }

    private fun size(): Int {
        var cur = this
        var size = 2
        while (true) {
            cur = cur.left as? CombinedRenderContext ?: return size
            size++
        }
    }

    private fun contains(element: RenderContext.Element): Boolean {
        return get(element.key) == element
    }

    private fun containsAll(context: CombinedRenderContext): Boolean {
        var cur = context
        while (true) {
            if (!contains(cur.element)) return false
            val next = cur.left
            if (next is CombinedRenderContext) {
                cur = next
            } else {
                return contains(next as RenderContext.Element)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return this === other || other is CombinedRenderContext && other.size() == size() && other.containsAll(this)
    }

    override fun hashCode(): Int {
        return left.hashCode() + element.hashCode()
    }

    override fun toString(): String {
        return "[" + fold("") { acc, element -> if (acc.isEmpty()) element.toString() else "$acc, $element" } + "]"
    }
}

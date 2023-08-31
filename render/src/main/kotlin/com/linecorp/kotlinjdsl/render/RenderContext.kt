package com.linecorp.kotlinjdsl.render

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface RenderContext {
    @SinceJdsl("3.0.0")
    operator fun <E : Element> get(key: Key<E>): E?

    @SinceJdsl("3.0.0")
    fun <E : Element> getValue(key: Key<E>): E {
        return get(key) ?: throw IllegalStateException("Key $key is missing in the context.")
    }

    @SinceJdsl("3.0.0")
    fun <R> fold(initial: R, operation: (R, Element) -> R): R

    @SinceJdsl("3.0.0")
    operator fun plus(context: RenderContext): RenderContext {
        if (context === EmptyRenderContext) return this // fast path -- avoid lambda creation

        return context.fold(this) { acc, element ->
            val removed = acc.minusKey(element.key)

            if (removed === EmptyRenderContext) {
                element
            } else {
                CombinedRenderContext(removed, element)
            }
        }
    }

    @SinceJdsl("3.0.0")
    fun minusKey(key: Key<*>): RenderContext

    @SinceJdsl("3.0.0")
    interface Key<E : Element>

    @SinceJdsl("3.0.0")
    interface Element : RenderContext {
        @SinceJdsl("3.0.0")
        val key: Key<*>

        override operator fun <E : Element> get(key: Key<E>): E? {
            @Suppress("UNCHECKED_CAST")
            return if (this.key == key) {
                this as E
            } else {
                null
            }
        }

        override fun <R> fold(initial: R, operation: (R, Element) -> R): R {
            return operation(initial, this)
        }

        override fun minusKey(key: Key<*>): RenderContext {
            return if (this.key == key) {
                EmptyRenderContext
            } else {
                this
            }
        }
    }
}

package com.linecorp.kotlinjdsl.render

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * The set of objects used when rendering.
 */
@SinceJdsl("3.0.0")
interface RenderContext {
    /**
     * Gets the value of the key.
     */
    @SinceJdsl("3.0.0")
    operator fun <E : Element> get(key: Key<E>): E?

    /**
     * Gets the element of the key.
     * If there is no value, an exception is thrown.
     */
    @SinceJdsl("3.0.0")
    fun <E : Element> getValue(key: Key<E>): E {
        return get(key) ?: throw IllegalStateException("Key $key is missing in the context.")
    }

    /**
     * Folds the context with the initial value and the operation.
     */
    @SinceJdsl("3.0.0")
    fun <R> fold(initial: R, operation: (R, Element) -> R): R

    /**
     * Combines this context with the context.
     */
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

    /**
     * Removes the element for key from Context
     */
    @SinceJdsl("3.0.0")
    fun minusKey(key: Key<*>): RenderContext

    /**
     * The key of the element.
     */
    @SinceJdsl("3.0.0")
    interface Key<E : Element>

    /**
     * Element contained within the RenderContext
     */
    @SinceJdsl("3.0.0")
    interface Element : RenderContext {
        /**
         * The key of the element.
         */
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

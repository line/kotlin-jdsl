package com.linecorp.kotlinjdsl.render

interface RenderContext {
    operator fun <E : Element> get(key: Key<E>): E?

    fun <E : Element> getValue(key: Key<E>): E {
        return get(key) ?: throw IllegalStateException("Key $key is missing in the context.")
    }

    fun <R> fold(initial: R, operation: (R, Element) -> R): R

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

    fun minusKey(key: Key<*>): RenderContext

    interface Key<E : Element>

    interface Element : RenderContext {
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

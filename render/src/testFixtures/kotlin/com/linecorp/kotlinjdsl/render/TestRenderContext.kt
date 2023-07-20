package com.linecorp.kotlinjdsl.render

class TestRenderContext(
    private val elements: Map<RenderContext.Key<*>, RenderContext.Element>,
) : RenderContext {
    constructor(vararg elements: RenderContext.Element) : this(elements.associateBy { it.key })
    constructor(elements: Iterable<RenderContext.Element>) : this(elements.associateBy { it.key })

    override fun <E : RenderContext.Element> get(key: RenderContext.Key<E>): E? {
        @Suppress("UNCHECKED_CAST")
        return elements[key] as? E
    }

    override fun plus(context: RenderContext): RenderContext {
        val newElements = context.fold<Map<RenderContext.Key<*>, RenderContext.Element>>(emptyMap()) { acc, element ->
            acc + (element.key to element)
        }

        return TestRenderContext(elements + newElements)
    }

    override fun <R> fold(initial: R, operation: (R, RenderContext.Element) -> R): R {
        return elements.values.fold(initial, operation)
    }

    override fun minusKey(key: RenderContext.Key<*>): RenderContext {
        return TestRenderContext(elements - key)
    }
}

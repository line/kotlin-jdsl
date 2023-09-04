package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

/**
 * Element of [RenderContext] to know which statement is being rendered.
 */
@SinceJdsl("3.0.0")
sealed class JpqlRenderStatement : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderStatement>

    @SinceJdsl("3.0.0")
    open fun isSelect(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isUpdate(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isDelete(): Boolean = false

    @SinceJdsl("3.0.0")
    data object Select : JpqlRenderStatement() {
        override fun isSelect(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object Update : JpqlRenderStatement() {
        override fun isUpdate(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object Delete : JpqlRenderStatement() {
        override fun isDelete(): Boolean = true
    }
}

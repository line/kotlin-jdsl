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
    object Select : JpqlRenderStatement() {
        override fun isSelect(): Boolean = true

        override fun toString(): String = "Select"
    }

    @SinceJdsl("3.0.0")
    object Update : JpqlRenderStatement() {
        override fun isUpdate(): Boolean = true

        override fun toString(): String = "Update"
    }

    @SinceJdsl("3.0.0")
    object Delete : JpqlRenderStatement() {
        override fun isDelete(): Boolean = true

        override fun toString(): String = "Delete"
    }
}

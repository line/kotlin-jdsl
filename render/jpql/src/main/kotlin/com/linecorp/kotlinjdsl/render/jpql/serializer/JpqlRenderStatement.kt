package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

sealed class JpqlRenderStatement : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderStatement>

    open fun isSelect(): Boolean = false
    open fun isUpdate(): Boolean = false
    open fun isDelete(): Boolean = false

    data object Select : JpqlRenderStatement() {
        override fun isSelect(): Boolean = true
    }

    data object Update : JpqlRenderStatement() {
        override fun isUpdate(): Boolean = true
    }

    data object Delete : JpqlRenderStatement() {
        override fun isDelete(): Boolean = true
    }
}

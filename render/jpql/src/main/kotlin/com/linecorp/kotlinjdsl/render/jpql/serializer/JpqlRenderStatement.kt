package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

sealed class JpqlRenderStatement : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderStatement>

    abstract fun isUpdate(): Boolean
    abstract fun isDelete(): Boolean
    abstract fun isSelect(): Boolean

    object Update : JpqlRenderStatement() {
        override fun isUpdate(): Boolean = true
        override fun isDelete(): Boolean = false
        override fun isSelect(): Boolean = false
    }

    object Delete : JpqlRenderStatement() {
        override fun isUpdate(): Boolean = false
        override fun isDelete(): Boolean = true
        override fun isSelect(): Boolean = false
    }

    object Select : JpqlRenderStatement() {
        override fun isUpdate(): Boolean = false
        override fun isDelete(): Boolean = false
        override fun isSelect(): Boolean = true
    }
}

package com.linecorp.kotlinjdsl.render.sql.serializer

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

sealed class SqlRenderStatement : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<SqlRenderStatement>

    abstract fun isInsert(): Boolean
    abstract fun isUpdate(): Boolean
    abstract fun isDelete(): Boolean
    abstract fun isSelect(): Boolean
    abstract fun isValues(): Boolean

    object Insert : SqlRenderStatement() {
        override fun isInsert(): Boolean = true
        override fun isUpdate(): Boolean = false
        override fun isDelete(): Boolean = false
        override fun isSelect(): Boolean = false
        override fun isValues(): Boolean = false
    }

    object Update : SqlRenderStatement() {
        override fun isInsert(): Boolean = false
        override fun isUpdate(): Boolean = true
        override fun isDelete(): Boolean = false
        override fun isSelect(): Boolean = false
        override fun isValues(): Boolean = false
    }

    object Delete : SqlRenderStatement() {
        override fun isInsert(): Boolean = false
        override fun isUpdate(): Boolean = false
        override fun isDelete(): Boolean = true
        override fun isSelect(): Boolean = false
        override fun isValues(): Boolean = false
    }

    object Select : SqlRenderStatement() {
        override fun isInsert(): Boolean = false
        override fun isUpdate(): Boolean = false
        override fun isDelete(): Boolean = false
        override fun isSelect(): Boolean = true
        override fun isValues(): Boolean = false
    }

    object Values : SqlRenderStatement() {
        override fun isInsert(): Boolean = false
        override fun isUpdate(): Boolean = false
        override fun isDelete(): Boolean = false
        override fun isSelect(): Boolean = false
        override fun isValues(): Boolean = true
    }
}

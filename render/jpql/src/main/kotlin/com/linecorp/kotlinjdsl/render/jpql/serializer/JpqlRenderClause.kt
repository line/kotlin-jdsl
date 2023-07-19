package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

sealed class JpqlRenderClause : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderClause>

    open fun isSelect(): Boolean = false
    open fun isUpdate(): Boolean = false
    open fun isDeleteFrom(): Boolean = false
    open fun isSet(): Boolean = false
    open fun isFrom(): Boolean = false
    open fun isWhere(): Boolean = false
    open fun isHaving(): Boolean = false
    open fun isGroupBy(): Boolean = false
    open fun isOrderBy(): Boolean = false

    data object Select : JpqlRenderClause() {
        override fun isSelect(): Boolean = true
    }

    data object Update : JpqlRenderClause() {
        override fun isUpdate(): Boolean = true
    }

    data object DeleteFrom : JpqlRenderClause() {
        override fun isDeleteFrom(): Boolean = true
    }

    data object Set : JpqlRenderClause() {
        override fun isSet(): Boolean = true
    }

    data object From : JpqlRenderClause() {
        override fun isFrom(): Boolean = true
    }

    data object Where : JpqlRenderClause() {
        override fun isWhere(): Boolean = true
    }

    data object Having : JpqlRenderClause() {
        override fun isHaving(): Boolean = true
    }

    data object GroupBy : JpqlRenderClause() {
        override fun isGroupBy(): Boolean = true
    }

    data object OrderBy : JpqlRenderClause() {
        override fun isOrderBy(): Boolean = true
    }
}

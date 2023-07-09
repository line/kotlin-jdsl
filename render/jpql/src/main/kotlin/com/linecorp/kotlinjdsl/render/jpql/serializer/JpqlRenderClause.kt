package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

sealed class JpqlRenderClause : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderClause>

    abstract fun isSelect(): Boolean
    abstract fun isFrom(): Boolean
    abstract fun isWhere(): Boolean
    abstract fun isHaving(): Boolean
    abstract fun isGroupBy(): Boolean
    abstract fun isOrderBy(): Boolean

    object Select : JpqlRenderClause() {
        override fun isSelect(): Boolean = true
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
    }

    object From : JpqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isFrom(): Boolean = true
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
    }

    object Where : JpqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = true
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
    }

    object Having : JpqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = true
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
    }

    object GroupBy : JpqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = true
        override fun isOrderBy(): Boolean = false
    }

    object OrderBy : JpqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = true
    }
}

package com.linecorp.kotlinjdsl.render.sql.serializer

import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

sealed class SqlRenderClause : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<SqlRenderClause>

    abstract fun isSelect(): Boolean
    abstract fun isInto(): Boolean
    abstract fun isFrom(): Boolean
    abstract fun isWhere(): Boolean
    abstract fun isHaving(): Boolean
    abstract fun isGroupBy(): Boolean
    abstract fun isOrderBy(): Boolean
    abstract fun isValues(): Boolean
    abstract fun isWith(): Boolean

    object Select : SqlRenderClause() {
        override fun isSelect(): Boolean = true
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = false
    }

    object Into : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = true
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = false
    }

    object From : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = true
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = false
    }

    object Where : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = true
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = false
    }

    object Having : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = true
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = false
    }

    object GroupBy : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = true
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = false
    }

    object OrderBy : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = true
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = false
    }

    object Values : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = true
        override fun isWith(): Boolean = false
    }

    object With : SqlRenderClause() {
        override fun isSelect(): Boolean = false
        override fun isInto(): Boolean = false
        override fun isFrom(): Boolean = false
        override fun isWhere(): Boolean = false
        override fun isHaving(): Boolean = false
        override fun isGroupBy(): Boolean = false
        override fun isOrderBy(): Boolean = false
        override fun isValues(): Boolean = false
        override fun isWith(): Boolean = true
    }
}

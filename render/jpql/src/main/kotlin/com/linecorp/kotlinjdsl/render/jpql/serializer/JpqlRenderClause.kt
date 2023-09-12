package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.render.AbstractRenderContextElement
import com.linecorp.kotlinjdsl.render.RenderContext

/**
 * Element of [RenderContext] to know which clause is being rendered.
 */
@SinceJdsl("3.0.0")
sealed class JpqlRenderClause : AbstractRenderContextElement(Key) {
    companion object Key : RenderContext.Key<JpqlRenderClause>

    @SinceJdsl("3.0.0")
    open fun isSelect(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isUpdate(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isDeleteFrom(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isSet(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isFrom(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isWhere(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isHaving(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isGroupBy(): Boolean = false

    @SinceJdsl("3.0.0")
    open fun isOrderBy(): Boolean = false

    @SinceJdsl("3.0.0")
    data object Select : JpqlRenderClause() {
        override fun isSelect(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object Update : JpqlRenderClause() {
        override fun isUpdate(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object DeleteFrom : JpqlRenderClause() {
        override fun isDeleteFrom(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object Set : JpqlRenderClause() {
        override fun isSet(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object From : JpqlRenderClause() {
        override fun isFrom(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object Where : JpqlRenderClause() {
        override fun isWhere(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object Having : JpqlRenderClause() {
        override fun isHaving(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object GroupBy : JpqlRenderClause() {
        override fun isGroupBy(): Boolean = true
    }

    @SinceJdsl("3.0.0")
    data object OrderBy : JpqlRenderClause() {
        override fun isOrderBy(): Boolean = true
    }
}

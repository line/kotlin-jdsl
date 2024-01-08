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
    object Select : JpqlRenderClause() {
        override fun isSelect(): Boolean = true

        override fun toString(): String = "Select"
    }

    @SinceJdsl("3.0.0")
    object Update : JpqlRenderClause() {
        override fun isUpdate(): Boolean = true

        override fun toString(): String = "Update"
    }

    @SinceJdsl("3.0.0")
    object DeleteFrom : JpqlRenderClause() {
        override fun isDeleteFrom(): Boolean = true

        override fun toString(): String = "DeleteFrom"
    }

    @SinceJdsl("3.0.0")
    object Set : JpqlRenderClause() {
        override fun isSet(): Boolean = true

        override fun toString(): String = "Set"
    }

    @SinceJdsl("3.0.0")
    object From : JpqlRenderClause() {
        override fun isFrom(): Boolean = true

        override fun toString(): String = "From"
    }

    @SinceJdsl("3.0.0")
    object Where : JpqlRenderClause() {
        override fun isWhere(): Boolean = true

        override fun toString(): String = "Where"
    }

    @SinceJdsl("3.0.0")
    object Having : JpqlRenderClause() {
        override fun isHaving(): Boolean = true

        override fun toString(): String = "Having"
    }

    @SinceJdsl("3.0.0")
    object GroupBy : JpqlRenderClause() {
        override fun isGroupBy(): Boolean = true

        override fun toString(): String = "GroupBy"
    }

    @SinceJdsl("3.0.0")
    object OrderBy : JpqlRenderClause() {
        override fun isOrderBy(): Boolean = true

        override fun toString(): String = "OrderBy"
    }
}

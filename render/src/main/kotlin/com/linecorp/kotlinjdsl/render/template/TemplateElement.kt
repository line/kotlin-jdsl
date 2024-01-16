package com.linecorp.kotlinjdsl.render.template

import com.linecorp.kotlinjdsl.Internal

@Internal
sealed interface TemplateElement {
    data class String(
        val value: kotlin.String,
    ) : TemplateElement

    data class ArgumentNumber(
        val value: Int,
    ) : TemplateElement
}

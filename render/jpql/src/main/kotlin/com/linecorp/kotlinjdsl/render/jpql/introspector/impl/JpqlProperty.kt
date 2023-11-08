package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlPropertyDescription

internal data class JpqlProperty(
    override val name: String,
) : JpqlPropertyDescription

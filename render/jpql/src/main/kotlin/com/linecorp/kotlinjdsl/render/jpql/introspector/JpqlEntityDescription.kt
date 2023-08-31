package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface JpqlEntityDescription {
    @SinceJdsl("3.0.0")
    val name: String
}

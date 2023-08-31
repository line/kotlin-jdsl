package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface JpqlSerializerModifier {
    @SinceJdsl("3.0.0")
    fun modifySerializer(serializer: JpqlSerializer<*>): JpqlSerializer<*>
}

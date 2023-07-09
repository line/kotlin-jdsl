package com.linecorp.kotlinjdsl.module.jpql.serializer

import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer

interface JpqlSerializerModifier {
    fun modifySerializer(serializer: JpqlSerializer<*>): JpqlSerializer<*>
}

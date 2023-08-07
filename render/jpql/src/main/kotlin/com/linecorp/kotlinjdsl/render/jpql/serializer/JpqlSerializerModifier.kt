package com.linecorp.kotlinjdsl.render.jpql.serializer

interface JpqlSerializerModifier {
    fun modifySerializer(serializer: JpqlSerializer<*>): JpqlSerializer<*>
}

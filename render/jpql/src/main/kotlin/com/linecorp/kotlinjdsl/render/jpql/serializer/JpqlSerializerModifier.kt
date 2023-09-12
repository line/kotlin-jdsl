package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.SinceJdsl

/**
 * Interface to modify the serializer.
 */
@SinceJdsl("3.0.0")
interface JpqlSerializerModifier {
    /**
     * Returns the modified serializer.
     */
    @SinceJdsl("3.0.0")
    fun modifySerializer(serializer: JpqlSerializer<*>): JpqlSerializer<*>
}

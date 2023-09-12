package com.linecorp.kotlinjdsl.render.jpql.serializer

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

/**
 * Serializer to write [QueryPart] to String with [JpqlWriter]
 */
@SinceJdsl("3.0.0")
interface JpqlSerializer<T : QueryPart> {
    /**
     * Returns a serializable KClass.
     * The QueryPart corresponding to the KClass is passed as parameter to [serialize].
     */
    @SinceJdsl("3.0.0")
    fun handledType(): KClass<T>

    /**
     * Serializes [QueryPart] to String with [JpqlWriter].
     *
     * [RenderContext] has elements that can be used for serialization.
     *
     * There are elements that can be used to know in which statement and clause and the [QueryPart] is being rendered.
     * RenderContext has [JpqlRenderStatement] and [JpqlRenderClause].
     * Each has methods that let it know which statement or clause is being rendered.
     *
     * There is an element that can be used to delegate serialization to another serializer.
     * RenderContext has [JpqlRenderSerializer].
     * It has serializers to serialize QueryPart, so it can be used to serialize QueryPart.
     *
     * There is an element that can be used to get the entity information.
     * RenderContext has [JpqlRenderIntrospector].
     * It can introspect KClass to get the entity information.
     *
     * @see JpqlRenderStatement
     * @see JpqlRenderClause
     * @see JpqlRenderSerializer
     * @see JpqlRenderIntrospector
     */
    @SinceJdsl("3.0.0")
    fun serialize(part: T, writer: JpqlWriter, context: RenderContext)
}

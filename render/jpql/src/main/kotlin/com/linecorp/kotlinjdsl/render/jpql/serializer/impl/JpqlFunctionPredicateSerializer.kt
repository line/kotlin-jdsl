package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlFunctionPredicate
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.support.JpqlFunctionSerializerSupport
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlFunctionPredicateSerializer : JpqlFunctionSerializerSupport(), JpqlSerializer<JpqlFunctionPredicate> {
    override fun handledType(): KClass<JpqlFunctionPredicate> {
        return JpqlFunctionPredicate::class
    }

    override fun serialize(part: JpqlFunctionPredicate, writer: JpqlWriter, context: RenderContext) {
        serialize(part.name, part.args, writer, context)
    }
}

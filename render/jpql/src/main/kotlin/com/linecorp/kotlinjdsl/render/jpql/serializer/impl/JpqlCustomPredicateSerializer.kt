package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlCustomPredicate
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.support.JpqlTemplateSerializerSupport
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlCustomPredicateSerializer : JpqlTemplateSerializerSupport(), JpqlSerializer<JpqlCustomPredicate> {
    override fun handledType(): KClass<JpqlCustomPredicate> {
        return JpqlCustomPredicate::class
    }

    override fun serialize(part: JpqlCustomPredicate, writer: JpqlWriter, context: RenderContext) {
        serialize(part.template, part.args, writer, context)
    }
}

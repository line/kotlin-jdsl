package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntityTreat
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlEntityTreatSerializer : JpqlSerializer<JpqlEntityTreat<*, *>> {
    override fun handledType(): KClass<JpqlEntityTreat<*, *>> {
        return JpqlEntityTreat::class
    }

    override fun serialize(part: JpqlEntityTreat<*, *>, writer: JpqlWriter, context: RenderContext) {
        val introspector = context.getValue(JpqlRenderIntrospector)
        val entity = introspector.introspect(part.type)

        val statement = context.getValue(JpqlRenderStatement)
        val clause = context.getValue(JpqlRenderClause)

        if (statement.isSelect() && clause.isFrom()) {
            writer.write(entity.name)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(part.alias)
        } else {
            writer.write("TREAT")
            writer.writeParentheses {
                writer.write(part.alias)
                writer.write(" ")
                writer.write("AS")
                writer.write(" ")
                writer.write(entity.name)
            }
        }
    }
}

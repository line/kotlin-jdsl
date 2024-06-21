package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlEntitySerializer : JpqlSerializer<JpqlEntity<*>> {
    override fun handledType(): KClass<JpqlEntity<*>> {
        return JpqlEntity::class
    }

    override fun serialize(part: JpqlEntity<*>, writer: JpqlWriter, context: RenderContext) {
        val statement = context.getValue(JpqlRenderStatement)
        val clause = context.getValue(JpqlRenderClause)

        if (
            (statement.isSelect() && clause.isFrom()) ||
            (statement.isSelect() && clause.isJoin()) ||
            (statement.isUpdate() && clause.isUpdate()) ||
            (statement.isDelete() && clause.isDeleteFrom())
        ) {
            val introspector = context.getValue(JpqlRenderIntrospector)
            val entity = introspector.introspect(part.type)

            writer.write(entity.name)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
        }

        writer.write(part.alias)
    }
}

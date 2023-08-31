package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

@Internal
class JpqlDerivedEntitySerializer : JpqlSerializer<JpqlDerivedEntity<*>> {
    override fun handledType(): KClass<JpqlDerivedEntity<*>> {
        return JpqlDerivedEntity::class
    }

    override fun serialize(part: JpqlDerivedEntity<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val statement = context.getValue(JpqlRenderStatement)
        val clause = context.getValue(JpqlRenderClause)

        if (
            (statement.isSelect() && clause.isFrom()) ||
            (statement.isUpdate() && clause.isUpdate()) ||
            (statement.isDelete() && clause.isDeleteFrom())
        ) {
            writer.writeParentheses {
                delegate.serialize(part.selectQuery, writer, context)
            }

            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
        }

        writer.write(part.alias)
    }
}

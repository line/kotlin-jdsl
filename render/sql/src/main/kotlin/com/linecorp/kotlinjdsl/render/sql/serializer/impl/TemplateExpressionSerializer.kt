package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.impl.TemplateExpression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlRenderSerializer
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class TemplateExpressionSerializer : SqlSerializer<TemplateExpression<*>> {
    private val cache: MutableMap<String, List<String>> = ConcurrentHashMap()

    private val elementRegex = Regex("\\{(\\d+)}")

    override fun handledType(): KClass<TemplateExpression<*>> {
        return TemplateExpression::class
    }

    override fun serialize(part: TemplateExpression<*>, writer: SqlWriter, context: RenderContext) {
        val splitTemplate = cache.computeIfAbsent(part.template) {
            it.split(elementRegex)
        }

        val delegate = context.getValue(SqlRenderSerializer)
        val args = part.args.toList()

        splitTemplate.forEachIndexed { index, templateItem ->
            if (index > 0) {
                delegate.serialize(args[index - 1], writer, context)
            }

            writer.write(templateItem)
        }
    }
}

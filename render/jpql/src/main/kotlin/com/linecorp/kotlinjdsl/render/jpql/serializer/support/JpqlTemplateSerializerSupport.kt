package com.linecorp.kotlinjdsl.render.jpql.serializer.support

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import com.linecorp.kotlinjdsl.render.template.Template
import com.linecorp.kotlinjdsl.render.template.TemplateElement
import java.util.concurrent.ConcurrentHashMap

@Internal
open class JpqlTemplateSerializerSupport {
    companion object {
        private val cache: MutableMap<String, Template> = ConcurrentHashMap()
    }

    protected fun serialize(
        template: String,
        args: Iterable<Expression<*>>,
        writer: JpqlWriter,
        context: RenderContext,
    ) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val compiledTemplate = cache.computeIfAbsent(template) { Template.compile(template) }
        val orderedArgs = args.toList()

        compiledTemplate.elements.forEach {
            when (it) {
                is TemplateElement.String -> {
                    writer.write(it.value)
                }

                is TemplateElement.ArgumentNumber -> {
                    delegate.serialize(orderedArgs[it.value], writer, context)
                }
            }
        }
    }
}

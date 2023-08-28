package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCustomExpression
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class JpqlCustomExpressionSerializer : JpqlSerializer<JpqlCustomExpression<*>> {
    private val cache: MutableMap<String, JpqlCustomExpressionTemplate> = ConcurrentHashMap()

    override fun handledType(): KClass<JpqlCustomExpression<*>> {
        return JpqlCustomExpression::class
    }

    override fun serialize(part: JpqlCustomExpression<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        val template = cache.computeIfAbsent(part.template) { JpqlCustomExpressionTemplate.compile(part.template) }
        val args = part.args.toList()

        template.strings.forEachIndexed { index, string ->
            if (index > 0) {
                val argumentNumber = template.argumentNumbers[index - 1]

                delegate.serialize(args[argumentNumber], writer, context)
            }

            writer.write(string)
        }
    }
}

private class JpqlCustomExpressionTemplate(
    val strings: List<String>,
    val argumentNumbers: List<Int>,
) {
    companion object {
        fun compile(template: String): JpqlCustomExpressionTemplate {
            val strings = mutableListOf<String>()
            val argumentNumbers = mutableListOf<Int>()

            var match: MatchResult? = elementRegex.find(template)
                ?: return JpqlCustomExpressionTemplate(listOf(template), emptyList())

            var lastStart = 0
            val length = template.length

            do {
                val foundMatch = match!!

                strings.add(template.substring(lastStart, foundMatch.range.first))
                argumentNumbers.add(foundMatch.value.let { it.substring(1, it.length - 1) }.toInt())

                lastStart = foundMatch.range.last + 1
                match = foundMatch.next()
            } while (lastStart < length && match != null)

            if (lastStart < length) {
                strings.add(template.substring(lastStart, length))
            }

            return JpqlCustomExpressionTemplate(strings, argumentNumbers)
        }

        private val elementRegex = Regex("\\{(\\d+)}")
    }
}

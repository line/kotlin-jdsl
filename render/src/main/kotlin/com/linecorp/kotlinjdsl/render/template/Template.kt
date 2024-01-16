package com.linecorp.kotlinjdsl.render.template

import com.linecorp.kotlinjdsl.Internal

@Internal
class Template(
    val elements: List<TemplateElement>,
) {
    companion object {
        private val argumentNumberRegex = Regex("\\{(\\d+)}")

        fun compile(template: String): Template {
            var match: MatchResult? = argumentNumberRegex.find(template)
                ?: return Template(listOf(TemplateElement.String(template)))

            val elements = mutableListOf<TemplateElement>()
            val length = template.length

            if (match!!.range.first != 0) {
                elements.add(
                    TemplateElement.String(template.substring(0, match.range.first)),
                )
            }

            elements.add(
                TemplateElement.ArgumentNumber(match.value.let { it.substring(1, it.length - 1) }.toInt()),
            )

            var lastStart = match.range.last + 1
            match = match.next()

            while (lastStart < length && match != null) {
                elements.add(
                    TemplateElement.String(template.substring(lastStart, match.range.first)),
                )

                elements.add(
                    TemplateElement.ArgumentNumber(match.value.let { it.substring(1, it.length - 1) }.toInt()),
                )

                lastStart = match.range.last + 1
                match = match.next()
            }

            if (lastStart < length) {
                elements.add(
                    TemplateElement.String(template.substring(lastStart, length)),
                )
            }

            return Template(elements)
        }
    }
}

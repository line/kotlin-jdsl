package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.iterable.IterableUtils
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIn
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import java.time.temporal.Temporal
import java.util.Date
import java.util.UUID
import kotlin.reflect.KClass

@Internal
class JpqlInSerializer : JpqlSerializer<JpqlIn<*>> {
    override fun handledType(): KClass<JpqlIn<*>> {
        return JpqlIn::class
    }

    override fun serialize(part: JpqlIn<*>, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

        if (IterableUtils.isEmpty(part.compareValues)) {
            writer.write("0 = 1")
        } else {
            delegate.serialize(part.value, writer, context)

            writer.write(" ")
            writer.write("IN")
            writer.write(" ")

            val compareValues = part.compareValues.toList()
            if (compareValues.all { it.isBasicType() }) {
                val values = compareValues.map { (it as JpqlValue<*>).value }
                val singleParam = Expressions.value(values)
                writer.writeParentheses {
                    delegate.serialize(singleParam, writer, context)
                }
            } else {
                writer.writeParentheses {
                    writer.writeEach(compareValues, separator = ", ") {
                        delegate.serialize(it, writer, context)
                    }
                }
            }
        }
    }

    /**
     * Returns `true` if this expression is a [JpqlValue] containing a basic type.
     *
     * Basic types are [String], [Number], [Boolean], [Enum], [UUID], [Temporal], and [Date].
     *
     * If all values in the IN clause are basic types, they can be grouped into a single parameter
     * (e.g. `IN (?1)` where `?1` is a `List`) for query plan caching optimization.
     * Other types (like Entities or Paths) often require individual rendering or specific serialization logic
     * (e.g. extracting an ID from an Entity) and therefore cannot be safely grouped into a single raw value collection.
     */
    private fun Expression<*>.isBasicType(): Boolean =
        this is JpqlValue<*> && (
            this.value is String ||
                this.value is Number ||
                this.value is Boolean ||
                this.value is Enum<*> ||
                this.value is UUID ||
                this.value is Temporal ||
                this.value is Date
            )
}

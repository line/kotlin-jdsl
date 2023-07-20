package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlLiteral
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import kotlin.reflect.KClass

class JpqlLiteralSerializer : JpqlSerializer<JpqlLiteral<*>> {
    override fun handledType(): KClass<JpqlLiteral<*>> {
        return JpqlLiteral::class
    }

    override fun serialize(part: JpqlLiteral<*>, writer: JpqlWriter, context: RenderContext) {
        when (part) {
            is JpqlLiteral.IntLiteral -> serialize(part, writer)
            is JpqlLiteral.LongLiteral -> serialize(part, writer)
            is JpqlLiteral.FloatLiteral -> serialize(part, writer)
            is JpqlLiteral.DoubleLiteral -> serialize(part, writer)
            is JpqlLiteral.BooleanLiteral -> serialize(part, writer)
            is JpqlLiteral.StringLiteral -> serialize(part, writer)
            is JpqlLiteral.NullLiteral -> serialize(part, writer)
        }
    }

    private fun serialize(part: JpqlLiteral.IntLiteral, writer: JpqlWriter) {
        writer.write(part.int)
    }

    private fun serialize(part: JpqlLiteral.LongLiteral, writer: JpqlWriter) {
        writer.write(part.long)
        writer.write("L")
    }

    private fun serialize(part: JpqlLiteral.FloatLiteral, writer: JpqlWriter) {
        writer.write(part.float)
        writer.write("F")
    }

    private fun serialize(part: JpqlLiteral.DoubleLiteral, writer: JpqlWriter) {
        writer.write(part.double)
    }

    private fun serialize(part: JpqlLiteral.BooleanLiteral, writer: JpqlWriter) {
        if (part.boolean) {
            writer.write("TRUE")
        } else {
            writer.write("FALSE")
        }
    }

    private fun serialize(part: JpqlLiteral.StringLiteral, writer: JpqlWriter) {
        val encodedString = part.string.replace("'", "''")

        writer.write("'")
        writer.write(encodedString)
        writer.write("'")
    }

    private fun serialize(part: JpqlLiteral.NullLiteral, writer: JpqlWriter) {
        writer.write("NULL")
    }
}

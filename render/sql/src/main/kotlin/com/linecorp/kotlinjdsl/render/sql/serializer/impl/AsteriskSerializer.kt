package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.query.sql.impl.Asterisk
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.generator.SqlWriter
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import kotlin.reflect.KClass

class AsteriskSerializer : SqlSerializer<Asterisk> {
    override fun handledType(): KClass<Asterisk> {
        return Asterisk::class
    }

    override fun serialize(part: Asterisk, writer: SqlWriter, context: RenderContext) {
        writer.write("*")
    }
}

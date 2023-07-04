package com.linecorp.kotlinjdsl.render.sql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.sql.impl.Asterisk
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer
import com.linecorp.kotlinjdsl.render.sql.writer.SqlWriter
import kotlin.reflect.KClass

class AsteriskSerializer : SqlSerializer<Asterisk> {
    override fun handledType(): KClass<Asterisk> {
        return Asterisk::class
    }

    override fun serialize(part: Asterisk, writer: SqlWriter, context: RenderContext) {
        writer.write("*")
    }
}

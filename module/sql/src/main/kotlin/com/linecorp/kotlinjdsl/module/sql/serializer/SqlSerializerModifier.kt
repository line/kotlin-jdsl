package com.linecorp.kotlinjdsl.module.sql.serializer

import com.linecorp.kotlinjdsl.render.sql.serializer.SqlSerializer

interface SqlSerializerModifier {
    fun modifySerializer(serializer: SqlSerializer<*>): SqlSerializer<*>
}

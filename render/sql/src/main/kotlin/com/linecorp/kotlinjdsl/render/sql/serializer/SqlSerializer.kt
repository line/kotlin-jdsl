package com.linecorp.kotlinjdsl.render.sql.serializer

import com.linecorp.kotlinjdsl.query.QueryPart
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.sql.generator.SqlWriter
import kotlin.reflect.KClass

interface SqlSerializer<T : QueryPart> {
    fun handledType(): KClass<T>

    /**
     * QuerySerializer의 결과가 되는 String은 앞 뒤로 공백이 없는 것을 기본으로 한다.
     */
    fun serialize(part: T, writer: SqlWriter, context: RenderContext)
}

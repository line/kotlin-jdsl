package com.linecorp.kotlinjdsl.module.sql

import com.linecorp.kotlinjdsl.render.sql.introspector.impl.NameBasedSqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.serializer.impl.*

internal class DefaultSqlModule : SqlModule {
    private val introspector = NameBasedSqlIntrospector()

    private val serializers = listOf(
        NormalSelectQuerySerializer(),
        NormalInsertQuerySerializer(),
        AsteriskSerializer(),
        TableAsteriskSerializer(),
        AliasedTableSerializer(),
        ColumnSerializer(),
        ParamSerializer(),
        TableReferenceSerializer(),
        TemplateExpressionSerializer(),
    )

    override fun setupModule(context: SqlModule.SetupContext) {
        context.prependIntrospector(introspector)
        context.addAllSerializer(serializers)
    }
}

package com.linecorp.kotlinjdsl.render.jpql.serializer

import kotlin.reflect.KClass

annotation class StatementClause(
    val statement: KClass<out JpqlRenderStatement> = JpqlRenderStatement::class,
    val clause: KClass<out JpqlRenderClause> = JpqlRenderClause::class,
)

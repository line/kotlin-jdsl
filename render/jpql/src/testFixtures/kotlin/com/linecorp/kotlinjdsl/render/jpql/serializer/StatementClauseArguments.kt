package com.linecorp.kotlinjdsl.render.jpql.serializer

import kotlin.reflect.KClass
import org.junit.jupiter.params.provider.Arguments

internal data class StatementClauseArguments(
    val statement: KClass<out JpqlRenderStatement>,
    val clause: KClass<out JpqlRenderClause>,
) : Arguments {
    override fun get(): Array<*> = arrayOf(statement.objectInstance, clause.objectInstance)
}

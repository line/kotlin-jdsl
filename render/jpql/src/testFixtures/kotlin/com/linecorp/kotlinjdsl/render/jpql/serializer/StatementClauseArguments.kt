package com.linecorp.kotlinjdsl.render.jpql.serializer

import org.junit.jupiter.params.provider.Arguments
import kotlin.reflect.KClass

internal data class StatementClauseArguments(
    val statement: KClass<out JpqlRenderStatement>,
    val clause: KClass<out JpqlRenderClause>,
) : Arguments {
    override fun get(): Array<*> = arrayOf(statement.objectInstance, clause.objectInstance)
}

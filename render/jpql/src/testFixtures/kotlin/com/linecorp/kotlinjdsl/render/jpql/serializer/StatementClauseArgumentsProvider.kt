package com.linecorp.kotlinjdsl.render.jpql.serializer

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.AnnotationConsumer
import java.util.stream.Stream

internal class StatementClauseArgumentsProvider : ArgumentsProvider, AnnotationConsumer<StatementClauseSource> {
    private lateinit var includes: LinkedHashSet<StatementClauseArguments>
    private lateinit var excludes: LinkedHashSet<StatementClauseArguments>

    override fun accept(annotation: StatementClauseSource) {
        initializeIncludes(annotation.includes)
        initializeExcludes(annotation.excludes)
    }

    private fun initializeIncludes(includes: Array<StatementClause>) {
        this.includes = allStatementClauses.filter {
            includes.any { include -> it.isConsideredAs(include) }
        }.toCollection(LinkedHashSet())
    }

    private fun initializeExcludes(excludes: Array<StatementClause>) {
        this.excludes = allStatementClauses.filter {
            excludes.any { exclude -> it.isConsideredAs(exclude) }
        }.toCollection(LinkedHashSet())
    }

    private fun StatementClauseArguments.isConsideredAs(argument: StatementClause): Boolean {
        if (this.statement == JpqlRenderStatement::class || argument.statement == JpqlRenderStatement::class) {
            return this.clause == argument.clause
        }

        if (this.clause == JpqlRenderClause::class || argument.clause == JpqlRenderClause::class) {
            return this.statement == argument.statement
        }

        if (this.statement != argument.statement) return false
        if (this.clause != argument.clause) return false

        return true
    }

    override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
        val arguments = mutableListOf<StatementClauseArguments>()

        if (includes.isEmpty()) {
            arguments.addAll(allStatementClauses)
        } else {
            arguments.addAll(includes)
        }

        return arguments.filter { !excludes.contains(it) }.stream()
    }
}

private val allStatements = JpqlRenderStatement::class.sealedSubclasses
private val allClauses = JpqlRenderClause::class.sealedSubclasses

private val allStatementClauses = allStatements.flatMap { statement ->
    allClauses.map { clause ->
        StatementClauseArguments(statement, clause)
    }
}

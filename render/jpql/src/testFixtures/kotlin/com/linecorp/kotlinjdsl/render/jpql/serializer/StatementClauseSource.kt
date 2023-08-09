package com.linecorp.kotlinjdsl.render.jpql.serializer

import org.junit.jupiter.params.provider.ArgumentsSource

@ArgumentsSource(StatementClauseArgumentsProvider::class)
annotation class StatementClauseSource(
    val includes: Array<StatementClause> = [],
    val excludes: Array<StatementClause> = [],
)

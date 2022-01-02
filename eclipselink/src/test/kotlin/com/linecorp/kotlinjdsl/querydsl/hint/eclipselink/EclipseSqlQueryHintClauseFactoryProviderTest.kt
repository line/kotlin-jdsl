package com.linecorp.kotlinjdsl.querydsl.hint.eclipselink

import com.linecorp.kotlinjdsl.query.clause.hint.EclipselinkSqlQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlQueryHintClauseFactoryProvider
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

internal class EclipseSqlQueryHintClauseFactoryProviderTest : WithAssertions {

    @Test
    fun test() {
        assertThat(SqlQueryHintClauseFactoryProvider.loadedFactory?.invoke(listOf("test"))).isInstanceOf(
            EclipselinkSqlQueryHintClause::class.java
        )
    }
}

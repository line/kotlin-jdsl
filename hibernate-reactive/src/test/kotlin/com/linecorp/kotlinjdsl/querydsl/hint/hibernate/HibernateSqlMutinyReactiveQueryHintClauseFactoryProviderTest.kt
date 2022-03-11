package com.linecorp.kotlinjdsl.querydsl.hint.hibernate

import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlMutinyReactiveQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlReactiveQueryHintClauseFactoryProvider
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

internal class HibernateSqlMutinyReactiveQueryHintClauseFactoryProviderTest : WithAssertions {

    @Test
    fun test() {
        assertThat(SqlReactiveQueryHintClauseFactoryProvider.loadedFactory?.invoke(listOf("test"))).isInstanceOf(
            HibernateSqlMutinyReactiveQueryHintClause::class.java
        )
    }
}

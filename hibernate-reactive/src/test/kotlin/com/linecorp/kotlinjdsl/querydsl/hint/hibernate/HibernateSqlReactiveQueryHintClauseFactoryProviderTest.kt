package com.linecorp.kotlinjdsl.querydsl.hint.hibernate

import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlReactiveQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlReactiveQueryHintClauseFactoryProvider
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

internal class HibernateSqlReactiveQueryHintClauseFactoryProviderTest : WithAssertions {

    @Test
    fun test() {
        assertThat(SqlReactiveQueryHintClauseFactoryProvider.loadedFactory?.invoke(listOf("test"))).isInstanceOf(
            HibernateSqlReactiveQueryHintClause::class.java
        )
    }
}

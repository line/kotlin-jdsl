package com.linecorp.kotlinjdsl.querydsl.hint.hibernate

import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlQueryHintClause
import com.linecorp.kotlinjdsl.querydsl.hint.SqlQueryHintClauseFactoryProvider
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

internal class HibernateSqlQueryHintClauseFactoryProviderTest : WithAssertions {

    @Test
    fun test() {
        assertThat(SqlQueryHintClauseFactoryProvider.loadedFactory?.invoke(listOf("test"))).isInstanceOf(
            HibernateSqlQueryHintClause::class.java
        )
    }
}

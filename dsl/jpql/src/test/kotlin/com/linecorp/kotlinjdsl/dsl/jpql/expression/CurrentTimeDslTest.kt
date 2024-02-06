package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.sql.Time

class CurrentTimeDslTest : WithAssertions {
    @Test
    fun currentTime() {
        // when
        val expression = queryPart {
            currentTime()
        }.toExpression()

        val actual: Expression<Time> = expression // for type check

        // then
        val expected = Expressions.currentTime()

        assertThat(actual).isEqualTo(expected)
    }
}

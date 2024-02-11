package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalTime

class LocalTimeDslTest {

    @Test
    fun localTime() {
        // when
        val expression = queryPart {
            localTime()
        }.toExpression()

        val actual: Expression<LocalTime> = expression

        // then
        val expected = Expressions.localTime()

        assertThat(actual).isEqualTo(expected)
    }
}

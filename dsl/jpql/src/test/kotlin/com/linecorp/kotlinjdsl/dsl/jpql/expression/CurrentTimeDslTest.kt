package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.time.LocalTime

class CurrentTimeDslTest : WithAssertions {
    @Test
    fun `currentDate() with a property`() {
        // when
        val expression = queryPart {
            currentTime()
        }.toExpression()

        val actual: Expression<LocalTime> = expression // for type check

        // then
        val expected = Expressions.currentTime()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `currentDate() with a expression`() {
        // when
        val expression = queryPart {
            currentTime()
        }.toExpression()

        val actual: Expression<LocalTime> = expression // for type check

        // then
        val expected = Expressions.currentTime()

        assertThat(actual).isEqualTo(expected)
    }
}

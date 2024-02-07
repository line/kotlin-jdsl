package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LocalDateTimeExpressionDslTest {

    @Test
    fun localDateTime() {
        // when
        val expression = queryPart {
            localDateTime()
        }.toExpression()

        val actual: Expression<LocalDateTime> = expression // for type check

        // then
        val expected = Expressions.localDateTime()

        assertThat(actual).isEqualTo(expected)
    }
}

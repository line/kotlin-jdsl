package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class LocalDateDslTest {

    @Test
    fun localDate() {
        // when
        val expression = queryPart {
            localDate()
        }.toExpression()

        val actual: Expression<LocalDate> = expression // for type check

        // then
        val expected = Expressions.localDate()

        assertThat(actual).isEqualTo(expected)
    }
}

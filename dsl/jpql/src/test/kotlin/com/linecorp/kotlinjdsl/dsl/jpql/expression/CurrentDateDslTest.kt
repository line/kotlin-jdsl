package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.sql.Date

class CurrentDateDslTest : WithAssertions {
    @Test
    fun `currentDate()`() {
        // when
        val expression = queryPart {
            currentDate()
        }.toExpression()

        val actual: Expression<Date> = expression // for type check

        // then
        val expected = Expressions.currentDate()

        assertThat(actual).isEqualTo(expected)
    }
}

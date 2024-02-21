package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.sql.Timestamp

class CurrentTimestampTest : WithAssertions {
    @Test
    fun currentTimestamp() {
        // when
        val expression = queryPart {
            currentTimestamp()
        }.toExpression()

        val actual: Expression<Timestamp> = expression // for type check

        // then
        val expected = Expressions.currentTimestamp()

        assertThat(actual).isEqualTo(expected)
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PowerDslTest : WithAssertions {
    private val intPath1 = Paths.path(Book::verticalLength)
    private val intExpression1 = Expressions.value(2)
    private val int1 = 2

    @Test
    fun `power() with a property`() {
        // when
        val expression1 = queryPart {
            power(Book::verticalLength, int1)
        }.toExpression()

        val expression2 = queryPart {
            round(Book::verticalLength, intExpression1)
        }.toExpression()

        val actual1: Expression<Int> = expression1 // for type check
        val actual2: Expression<Int> = expression2 // for type check

        // then
        val expected1 = Expressions.round(
            value = Paths.path(Book::verticalLength),
            scale = Expressions.value(int1),
        )

        val expected2 = Expressions.round(
            value = Paths.path(Book::verticalLength),
            scale = intExpression1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `power() with a expression`() {
        // when
        val expression1 = queryPart {
            round(intPath1, int1)
        }.toExpression()

        val expression2 = queryPart {
            round(intPath1, intExpression1)
        }.toExpression()

        val actual1: Expression<Int> = expression1 // for type check
        val actual2: Expression<Int> = expression2 // for type check

        // then
        val expected1 = Expressions.round(
            value = intPath1,
            scale = Expressions.value(int1),
        )

        val expected2 = Expressions.round(
            value = intPath1,
            scale = intExpression1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }
}

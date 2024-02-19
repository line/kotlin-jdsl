package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class RoundDslTest : WithAssertions {
    private val bigDecimalExpression1 = Paths.path(Book::price)
    private val intExpression1 = Expressions.value(2)

    private val int1 = 2

    @Test
    fun `round() with a property`() {
        // when
        val expression1 = queryPart {
            round(Book::price, int1)
        }.toExpression()

        val expression2 = queryPart {
            round(Book::price, intExpression1)
        }.toExpression()

        val actual1: Expression<BigDecimal> = expression1 // for type check
        val actual2: Expression<BigDecimal> = expression2 // for type check

        // then
        val expected1 = Expressions.round(
            value = Paths.path(Book::price),
            scale = Expressions.value(int1),
        )

        val expected2 = Expressions.round(
            value = Paths.path(Book::price),
            scale = intExpression1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `round() with a expression`() {
        // when
        val expression1 = queryPart {
            round(bigDecimalExpression1, int1)
        }.toExpression()

        val expression2 = queryPart {
            round(bigDecimalExpression1, intExpression1)
        }.toExpression()

        val actual1: Expression<BigDecimal> = expression1 // for type check
        val actual2: Expression<BigDecimal> = expression2 // for type check

        // then
        val expected1 = Expressions.round(
            value = bigDecimalExpression1,
            scale = Expressions.value(int1),
        )

        val expected2 = Expressions.round(
            value = bigDecimalExpression1,
            scale = intExpression1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }
}

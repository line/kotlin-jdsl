package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class RoundDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::salePrice)
    private val intExpression1: Expression<Int> = Expressions.value(3)
    private val int1 = 3

    @Test
    fun `round() with a expression`() {
        // when
        val expression = queryPart {
            round(expression1, intExpression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.round(
            value = expression1,
            scale = intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `round() with a property`() {
        // when
        val expression = queryPart {
            round(Book::price, intExpression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.round(
            value = Paths.path(Book::price),
            scale = Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }
}

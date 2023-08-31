package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class MaxDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::salePrice)

    @Test
    fun `max() with a property`() {
        // when
        val expression = queryPart {
            max(Book::price)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = false,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `max() with a expression`() {
        // when
        val expression = queryPart {
            max(expression1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = false,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `maxDistinct() with a property`() {
        // when
        val expression = queryPart {
            maxDistinct(Book::price)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = true,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `maxDistinct() with a expression`() {
        // when
        val expression = queryPart {
            maxDistinct(expression1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.max(
            distinct = true,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

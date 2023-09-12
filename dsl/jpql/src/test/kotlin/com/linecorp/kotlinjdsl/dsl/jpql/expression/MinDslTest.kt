package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MinDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::salePrice)

    @Test
    fun `min() with a property`() {
        // when
        val expression = queryPart {
            min(Book::price)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = false,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `min() with a expression`() {
        // when
        val expression = queryPart {
            min(expression1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = false,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `minDistinct() with a property`() {
        // when
        val expression = queryPart {
            minDistinct(Book::price)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = true,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `minDistinct() with a expression`() {
        // when
        val expression = queryPart {
            minDistinct(expression1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.min(
            distinct = true,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

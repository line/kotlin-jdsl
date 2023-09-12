package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class AvgDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::salePrice)

    @Test
    fun `avg() with a property`() {
        // when
        val expression = queryPart {
            avg(Book::price)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = false,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avg() with a expression`() {
        // when
        val expression = queryPart {
            avg(expression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = false,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avgDistinct() with a property`() {
        // when
        val expression = queryPart {
            avgDistinct(Book::price)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = true,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `avgDistinct() with a expression`() {
        // when
        val expression = queryPart {
            avgDistinct(expression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.avg(
            distinct = true,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

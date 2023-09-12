package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class CountDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::salePrice)

    @Test
    fun `count() with a property`() {
        // when
        val expression = queryPart {
            count(Book::price)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = false,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `count() with a expression`() {
        // when
        val expression = queryPart {
            count(expression1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = false,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `countDistinct() with a property`() {
        // when
        val expression = queryPart {
            countDistinct(Book::price)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = true,
            expr = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `countDistinct() with a expression`() {
        // when
        val expression = queryPart {
            countDistinct(expression1)
        }.toExpression()

        val actual: Expression<Long> = expression // for type check

        // then
        val expected = Expressions.count(
            distinct = true,
            expr = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

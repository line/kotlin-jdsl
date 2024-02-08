package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SqrtDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::salePrice)

    @Test
    fun `sqrt() with a property`() {
        // when
        val expression = queryPart {
            sqrt(Book::price)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sqrt(
            value = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `sqrt() with a expression`() {
        // when
        val expression = queryPart {
            sqrt(expression1)
        }.toExpression()

        val actual: Expression<Double> = expression // for type check

        // then
        val expected = Expressions.sqrt(
            value = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

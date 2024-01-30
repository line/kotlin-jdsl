package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.math.ceil

class CeilingDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)

    @Test
    fun `ceiling() with a property`() {
        // when
        val expression = queryPart {
            ceiling(Book::price)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.ceiling(
            value = Paths.path(Book::price),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `ceiling() with a expression`() {
        // when
        val expression = queryPart {
            ceiling(expression1)
        }.toExpression()

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.ceiling(
            value = expression1
        )

        assertThat(actual).isEqualTo(expected)
    }
}

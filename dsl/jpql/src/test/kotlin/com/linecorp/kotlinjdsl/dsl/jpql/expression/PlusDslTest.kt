package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PlusDslTest : WithAssertions {
    private val bigDecimal1 = BigDecimal.valueOf(100)

    private val bigDecimalExpression1 = Expressions.value(bigDecimal1)

    @Test
    fun `plus() with a property and a bigDecimal`() {
        // when
        val expression1 = queryPart {
            path(Book::price).plus(bigDecimal1)
        }

        val expression2 = queryPart {
            plus(path(Book::price), bigDecimal1)
        }

        val actual1: Expression<BigDecimal> = expression1 // for type check
        val actual2: Expression<BigDecimal> = expression2 // for type check

        // then
        val expected1 = Expressions.plus(
            value1 = Paths.path(Book::price),
            value2 = Expressions.value(bigDecimal1),
        )

        val expected2 = Expressions.plus(
            value1 = Expressions.parentheses(Paths.path(Book::price)),
            value2 = Expressions.parentheses(Expressions.value(bigDecimal1)),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `plus() with a property and a bigDecimal expression`() {
        // when
        val expression1 = queryPart {
            path(Book::price).plus(bigDecimalExpression1)
        }

        val expression2 = queryPart {
            plus(path(Book::price), bigDecimalExpression1)
        }

        val actual1: Expression<BigDecimal> = expression1 // for type check
        val actual2: Expression<BigDecimal> = expression2 // for type check

        // then
        val expected1 = Expressions.plus(
            value1 = Paths.path(Book::price),
            value2 = bigDecimalExpression1,
        )

        val expected2 = Expressions.plus(
            value1 = Expressions.parentheses(Paths.path(Book::price)),
            value2 = Expressions.parentheses(bigDecimalExpression1),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }
}

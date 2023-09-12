package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AliasDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)

    private val alias1 = Expressions.expression(BigDecimal::class, "alias1")

    @Test
    fun `as() with a expression and an alias`() {
        // when
        val expression = queryPart {
            expression1.`as`(alias1)
        }

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.alias(
            expression1,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `alias() with a expression and an alias`() {
        // when
        val expression = queryPart {
            expression1.alias(alias1)
        }

        val actual: Expression<BigDecimal> = expression // for type check

        // then
        val expected = Expressions.alias(
            expression1,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

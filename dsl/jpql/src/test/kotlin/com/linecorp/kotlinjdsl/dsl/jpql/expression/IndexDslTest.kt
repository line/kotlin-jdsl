package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class IndexDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::authors)

    @Test
    fun `index() with a property`() {
        // when
        val expression = queryPart {
            index(Book::authors)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.index(
            path = Paths.path(Book::authors),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `index() with a expression`() {
        // when
        val expression = queryPart {
            index(expression1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.index(
            path = expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths.path
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class LocateDslTest : WithAssertions {
    private val stringExpression1 = Expressions.value("ok")
    private val stringExpression2 = path(Book::title)
    private val intExpression1 = Expressions.value(1)

    @Test
    fun `locate() with string expressions`() {
        // when
        val expression = queryPart {
            locate(stringExpression1, stringExpression2)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.locate(
            substring = stringExpression1,
            string = stringExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `locate() with string expressions and a int expression`() {
        // when
        val expression = queryPart {
            locate(stringExpression1, stringExpression2, intExpression1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.locate(
            substring = stringExpression1,
            string = stringExpression2,
            start = intExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

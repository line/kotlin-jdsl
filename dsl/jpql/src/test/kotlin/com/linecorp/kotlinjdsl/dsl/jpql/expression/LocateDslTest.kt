package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths.path
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class LocateDslTest : WithAssertions {
    private val string1 = "string1"
    private val string2 = "string2"
    private val int1 = 1

    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = path(Book::title)
    private val intExpression1 = Expressions.value(1)

    @Test
    fun `locate() with strings`() {
        // when
        val expression = queryPart {
            locate(string1, string2)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.locate(
            substring = Expressions.value(string1),
            string = Expressions.value(string2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `locate() with strings and an int`() {
        // when
        val expression = queryPart {
            locate(string1, string2, int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.locate(
            substring = Expressions.value(string1),
            string = Expressions.value(string2),
            start = Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `locate() with a string and a string expression`() {
        // when
        val expression = queryPart {
            locate(string1, stringExpression1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.locate(
            substring = Expressions.value(string1),
            string = stringExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `locate() with a string and a string expression and an int`() {
        // when
        val expression = queryPart {
            locate(string1, stringExpression1, int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.locate(
            substring = Expressions.value(string1),
            string = stringExpression1,
            start = Expressions.value(int1),
        )

        assertThat(actual).isEqualTo(expected)
    }

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
    fun `locate() with string expressions and an int expression`() {
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

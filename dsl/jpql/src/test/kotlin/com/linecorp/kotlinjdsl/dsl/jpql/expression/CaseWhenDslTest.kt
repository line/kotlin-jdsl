package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CaseWhenDslTest : WithAssertions {
    private val path1 = Paths.path(Book::price)

    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    private val predicate2 = Predicates.equal(
        Paths.path(Book::salePrice),
        Expressions.value(BigDecimal.valueOf(200)),
    )

    private val string1 = "string1"
    private val string2 = "string2"

    private val stringExpression1 = Expressions.value(string1)
    private val stringExpression2 = Expressions.value(string2)

    @Test
    fun `caseWhen() with a predicate and a string`() {
        // when
        val expression = queryPart {
            caseWhen(predicate1).then(string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseWhen(
            whens = mapOf(
                predicate1 to Expressions.value(string1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseWhen() with a predicate and a string expression`() {
        // when
        val expression = queryPart {
            caseWhen(predicate1).then(stringExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseWhen(
            whens = mapOf(
                predicate1 to stringExpression1,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseWhen() with predicates and strings`() {
        // when
        val expression = queryPart {
            caseWhen(predicate1).then(string1)
                .`when`(predicate2).then(string2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseWhen(
            whens = mapOf(
                predicate1 to Expressions.value(string1),
                predicate2 to Expressions.value(string2),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseWhen() with predicates and string expressions`() {
        // when
        val expression = queryPart {
            caseWhen(predicate1).then(stringExpression1)
                .`when`(predicate2).then(stringExpression2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseWhen(
            whens = mapOf(
                predicate1 to stringExpression1,
                predicate2 to stringExpression2,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseWhen() with a bigDecimal, a string, and else()`() {
        // when
        val expression = queryPart {
            caseWhen(predicate1).then(string1)
                .`else`(string2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseWhen(
            whens = mapOf(
                predicate1 to Expressions.value(string1),
            ),
            `else` = Expressions.value(string2),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseWhen() with a bigDecimal expression, a string expression, and else()`() {
        // when
        val expression = queryPart {
            caseWhen(predicate1).then(stringExpression1)
                .`else`(stringExpression2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseWhen(
            whens = mapOf(
                predicate1 to stringExpression1,
            ),
            `else` = stringExpression2,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }
}

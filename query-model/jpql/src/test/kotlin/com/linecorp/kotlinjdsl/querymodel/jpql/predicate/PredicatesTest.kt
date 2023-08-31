package com.linecorp.kotlinjdsl.querymodel.jpql.predicate

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlNull
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class PredicatesTest : WithAssertions {
    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = Expressions.value("string2")

    private val charExpression1 = Expressions.value('_')

    private val bigDecimalExpression1 = Paths.path(Book::price)
    private val bigDecimalExpression2 = Paths.path(Book::salePrice)
    private val bigDecimalExpression3 = Expressions.value(BigDecimal.valueOf(100))

    private val path1 = Paths.path(Book::authors)

    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    private val predicate2 = Predicates.equal(
        Paths.path(Book::salePrice),
        Expressions.value(BigDecimal.valueOf(200)),
    )

    private val subquery1 = Expressions.subquery(
        SelectQueries.selectQuery(
            returnType = BigDecimal::class,
            distinct = false,
            select = listOf(Paths.path(Book::price)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun not() {
        // when
        val actual = Predicates.not(
            predicate1,
        )

        // then
        val expected = JpqlNot(
            predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun and() {
        // when
        val actual = Predicates.and(
            listOf(
                predicate1,
                predicate2,
            ),
        )

        // then
        val expected = JpqlAnd(
            listOf(
                predicate1,
                predicate2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun or() {
        // when
        val actual = Predicates.or(
            listOf(
                predicate1,
                predicate2,
            ),
        )

        // then
        val expected = JpqlOr(
            listOf(
                predicate1,
                predicate2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun isNull() {
        // when
        val actual = Predicates.isNull(
            bigDecimalExpression1,
        )

        // then
        val expected = JpqlIsNull(
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun isNotNull() {
        // when
        val actual = Predicates.isNotNull(
            bigDecimalExpression1,
        )

        // then
        val expected = JpqlIsNotNull(
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun equal() {
        // when
        val actual = Predicates.equal(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        // then
        val expected = JpqlEqual(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `equal() with the JpqlNull`() {
        // when
        val actual = Predicates.equal(
            bigDecimalExpression1,
            JpqlNull,
        )

        // then
        val expected = JpqlIsNull(
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun equalAll() {
        // when
        val actual = Predicates.equalAll(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlEqualAll(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun equalAny() {
        // when
        val actual = Predicates.equalAny(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlEqualAny(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notEqual() {
        // when
        val actual = Predicates.notEqual(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        // then
        val expected = JpqlNotEqual(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notEqual() with the JpqlNull`() {
        // when
        val actual = Predicates.notEqual(
            bigDecimalExpression1,
            JpqlNull,
        )

        // then
        val expected = JpqlIsNotNull(
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notEqualAll() {
        // when
        val actual = Predicates.notEqualAll(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlNotEqualAll(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notEqualAny() {
        // when
        val actual = Predicates.notEqualAny(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlNotEqualAny(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThan() {
        // when
        val actual = Predicates.lessThan(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        // then
        val expected = JpqlLessThan(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanAll() {
        // when
        val actual = Predicates.lessThanAll(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanAll(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanAny() {
        // when
        val actual = Predicates.lessThanAny(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanAny(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualTo() {
        // when
        val actual = Predicates.lessThanOrEqualTo(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        // then
        val expected = JpqlLessThanOrEqualTo(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualToAll() {
        // when
        val actual = Predicates.lessThanOrEqualToAll(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanOrEqualToAll(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualToAny() {
        // when
        val actual = Predicates.lessThanOrEqualToAny(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanOrEqualToAny(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThan() {
        // when
        val actual = Predicates.greaterThan(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        // then
        val expected = JpqlGreaterThan(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanAll() {
        // when
        val actual = Predicates.greaterThanAll(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanAll(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanAny() {
        // when
        val actual = Predicates.greaterThanAny(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanAny(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualTo() {
        // when
        val actual = Predicates.greaterThanOrEqualTo(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        // then
        val expected = JpqlGreaterThanOrEqualTo(
            bigDecimalExpression1,
            bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualToAll() {
        // when
        val actual = Predicates.greaterThanOrEqualToAll(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanOrEqualToAll(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualToAny() {
        // when
        val actual = Predicates.greaterThanOrEqualToAny(
            bigDecimalExpression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanOrEqualToAny(
            bigDecimalExpression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun between() {
        // when
        val actual = Predicates.between(
            value = bigDecimalExpression1,
            min = bigDecimalExpression2,
            max = bigDecimalExpression3,
        )

        // then
        val expected = JpqlBetween(
            value = bigDecimalExpression1,
            min = bigDecimalExpression2,
            max = bigDecimalExpression3,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notBetween() {
        // when
        val actual = Predicates.notBetween(
            value = bigDecimalExpression1,
            min = bigDecimalExpression2,
            max = bigDecimalExpression3,
        )

        // then
        val expected = JpqlNotBetween(
            value = bigDecimalExpression1,
            min = bigDecimalExpression2,
            max = bigDecimalExpression3,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in`() {
        // when
        val actual = Predicates.`in`(
            value = bigDecimalExpression1,
            compareValues = listOf(
                bigDecimalExpression2,
                bigDecimalExpression3,
            ),
        )

        // then
        val expected = JpqlIn(
            value = bigDecimalExpression1,
            compareValues = listOf(
                bigDecimalExpression2,
                bigDecimalExpression3,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `in() with a subquery`() {
        // when
        val actual = Predicates.`in`(
            value = bigDecimalExpression1,
            subquery = subquery1,
        )

        // then
        val expected = JpqlInSubquery(
            value = bigDecimalExpression1,
            subquery = subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notIn() {
        // when
        val actual = Predicates.notIn(
            value = bigDecimalExpression1,
            compareValues = listOf(
                bigDecimalExpression2,
                bigDecimalExpression3,
            ),
        )

        // then
        val expected = JpqlNotIn(
            value = bigDecimalExpression1,
            compareValues = listOf(
                bigDecimalExpression2,
                bigDecimalExpression3,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notIn() with a subquery`() {
        // when
        val actual = Predicates.notIn(
            value = bigDecimalExpression1,
            subquery = subquery1,
        )

        // then
        val expected = JpqlNotInSubquery(
            value = bigDecimalExpression1,
            subquery = subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun like() {
        // when
        val actual = Predicates.like(
            value = stringExpression1,
            pattern = stringExpression2,
            escape = charExpression1,
        )

        // then
        val expected = JpqlLike(
            value = stringExpression1,
            pattern = stringExpression2,
            escape = charExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notLike() {
        // when
        val actual = Predicates.notLike(
            value = stringExpression1,
            pattern = stringExpression2,
            escape = charExpression1,
        )

        // then
        val expected = JpqlNotLike(
            value = stringExpression1,
            pattern = stringExpression2,
            escape = charExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun isEmpty() {
        // when
        val actual = Predicates.isEmpty(
            path = path1,
        )

        // then
        val expected = JpqlIsEmpty(
            path = path1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun isNotEmpty() {
        // when
        val actual = Predicates.isNotEmpty(
            path = path1,
        )

        // then
        val expected = JpqlIsNotEmpty(
            path = path1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun exists() {
        // when
        val actual = Predicates.exists(
            subquery = subquery1,
        )

        // then
        val expected = JpqlExists(
            subquery = subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notExists() {
        // when
        val actual = Predicates.notExists(
            subquery = subquery1,
        )

        // then
        val expected = JpqlNotExists(
            subquery = subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun parentheses() {
        // when
        val actual = Predicates.parentheses(
            predicate = predicate1,
        )

        // then
        val expected = JpqlPredicateParentheses(
            predicate = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

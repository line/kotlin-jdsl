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
    private val expression1 = Paths.path(Book::price)
    private val expression2 = Paths.path(Book::salePrice)
    private val expression3 = Expressions.value(BigDecimal.valueOf(100))

    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    private val predicate2 = Predicates.equal(
        Paths.path(Book::salePrice),
        Expressions.value(BigDecimal.valueOf(200)),
    )

    private val subquery1 = Expressions.subquery(
        SelectQueries.select(
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
            expression1,
        )

        // then
        val expected = JpqlIsNull(
            expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun isNotNull() {
        // when
        val actual = Predicates.isNotNull(
            expression1,
        )

        // then
        val expected = JpqlIsNotNull(
            expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun equal() {
        // when
        val actual = Predicates.equal(
            expression1,
            expression2,
        )

        // then
        val expected = JpqlEqual(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `equal() with the JpqlNull`() {
        // when
        val actual = Predicates.equal(
            expression1,
            JpqlNull,
        )

        // then
        val expected = JpqlIsNull(
            expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun equalAll() {
        // when
        val actual = Predicates.equalAll(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlEqualAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun equalAny() {
        // when
        val actual = Predicates.equalAny(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlEqualAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notEqual() {
        // when
        val actual = Predicates.notEqual(
            expression1,
            expression2,
        )

        // then
        val expected = JpqlNotEqual(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `notEqual() with the JpqlNull`() {
        // when
        val actual = Predicates.notEqual(
            expression1,
            JpqlNull,
        )

        // then
        val expected = JpqlIsNotNull(
            expression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notEqualAll() {
        // when
        val actual = Predicates.notEqualAll(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlNotEqualAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notEqualAny() {
        // when
        val actual = Predicates.notEqualAny(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlNotEqualAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThan() {
        // when
        val actual = Predicates.lessThan(
            expression1,
            expression2,
        )

        // then
        val expected = JpqlLessThan(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanAll() {
        // when
        val actual = Predicates.lessThanAll(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanAny() {
        // when
        val actual = Predicates.lessThanAny(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualTo() {
        // when
        val actual = Predicates.lessThanOrEqualTo(
            expression1,
            expression2,
        )

        // then
        val expected = JpqlLessThanOrEqualTo(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualToAll() {
        // when
        val actual = Predicates.lessThanOrEqualToAll(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualToAny() {
        // when
        val actual = Predicates.lessThanOrEqualToAny(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlLessThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThan() {
        // when
        val actual = Predicates.greaterThan(
            expression1,
            expression2,
        )

        // then
        val expected = JpqlGreaterThan(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanAll() {
        // when
        val actual = Predicates.greaterThanAll(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanAny() {
        // when
        val actual = Predicates.greaterThanAny(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualTo() {
        // when
        val actual = Predicates.greaterThanOrEqualTo(
            expression1,
            expression2,
        )

        // then
        val expected = JpqlGreaterThanOrEqualTo(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualToAll() {
        // when
        val actual = Predicates.greaterThanOrEqualToAll(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualToAny() {
        // when
        val actual = Predicates.greaterThanOrEqualToAny(
            expression1,
            subquery1,
        )

        // then
        val expected = JpqlGreaterThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun between() {
        // when
        val actual = Predicates.between(
            value = expression1,
            min = expression2,
            max = expression3,
        )

        // then
        val expected = JpqlBetween(
            value = expression1,
            min = expression2,
            max = expression3,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun notBetween() {
        // when
        val actual = Predicates.notBetween(
            value = expression1,
            min = expression2,
            max = expression3,
        )

        // then
        val expected = JpqlNotBetween(
            value = expression1,
            min = expression2,
            max = expression3,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

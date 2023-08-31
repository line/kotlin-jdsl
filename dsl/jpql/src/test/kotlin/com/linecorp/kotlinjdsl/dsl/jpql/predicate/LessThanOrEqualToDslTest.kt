package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class LessThanOrEqualToDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)

    private val bigDecimal1 = BigDecimal.valueOf(100)

    private val bigDecimalExpression1 = Expressions.value(bigDecimal1)

    private val subquery1 = Expressions.subquery(
        SelectQueries.selectQuery(
            returnType = BigDecimal::class,
            distinct = false,
            select = listOf(Paths.path(Book::price)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun `lessThanOrEqualTo() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.lessThanOrEqualTo(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lessThanOrEqualTo() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.lessThanOrEqualTo(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualToAll() {
        // when
        val actual = queryPart {
            expression1.lessThanOrEqualToAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanOrEqualToAny() {
        // when
        val actual = queryPart {
            expression1.lessThanOrEqualToAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `le() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.le(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `le() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.le(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun leAll() {
        // when
        val actual = queryPart {
            expression1.leAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun leAny() {
        // when
        val actual = queryPart {
            expression1.leAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

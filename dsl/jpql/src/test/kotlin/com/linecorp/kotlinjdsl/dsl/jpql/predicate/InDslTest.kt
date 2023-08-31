package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class InDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)

    private val bigDecimal1 = BigDecimal.valueOf(100)
    private val bigDecimal2 = BigDecimal.valueOf(100)

    private val bigDecimalExpression1 = Expressions.value(BigDecimal.valueOf(100))
    private val bigDecimalExpression2 = Expressions.value(BigDecimal.valueOf(100))

    private val subquery1 = Expressions.subquery(
        SelectQueries.selectQuery(
            returnType = BigDecimal::class,
            distinct = false,
            select = listOf(Paths.path(Book::price)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun `in() with bigDecimals`() {
        // when
        val predicate = queryPart {
            expression1.`in`(bigDecimal1, bigDecimal2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.`in`(
            value = expression1,
            compareValues = listOf(
                Expressions.value(bigDecimal1),
                Expressions.value(bigDecimal2),
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `in() with bigDecimal expressions`() {
        // when
        val predicate = queryPart {
            expression1.`in`(bigDecimalExpression1, bigDecimalExpression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.`in`(
            value = expression1,
            compareValues = listOf(
                bigDecimalExpression1,
                bigDecimalExpression2,
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `in() with a bigDecimal list`() {
        // when
        val predicate = queryPart {
            expression1.`in`(listOf(bigDecimal1, bigDecimal2))
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.`in`(
            value = expression1,
            compareValues = listOf(
                Expressions.value(bigDecimal1),
                Expressions.value(bigDecimal2),
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `in() with a bigDecimal expression list`() {
        // when
        val predicate = queryPart {
            expression1.`in`(listOf(bigDecimalExpression1, bigDecimalExpression2))
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.`in`(
            value = expression1,
            compareValues = listOf(
                bigDecimalExpression1,
                bigDecimalExpression2,
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `in() with a subquery`() {
        // when
        val predicate = queryPart {
            expression1.`in`(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.`in`(
            value = expression1,
            subquery = subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notIn() with bigDecimals`() {
        // when
        val predicate = queryPart {
            expression1.notIn(bigDecimal1, bigDecimal2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notIn(
            value = expression1,
            compareValues = listOf(
                Expressions.value(bigDecimal1),
                Expressions.value(bigDecimal2),
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notIn() with bigDecimal expressions`() {
        // when
        val predicate = queryPart {
            expression1.notIn(bigDecimalExpression1, bigDecimalExpression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notIn(
            value = expression1,
            compareValues = listOf(
                bigDecimalExpression1,
                bigDecimalExpression2,
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notIn() with a bigDecimal list`() {
        // when
        val predicate = queryPart {
            expression1.notIn(listOf(bigDecimal1, bigDecimal2))
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notIn(
            value = expression1,
            compareValues = listOf(
                Expressions.value(bigDecimal1),
                Expressions.value(bigDecimal2),
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notIn() with a bigDecimal expression list`() {
        // when
        val predicate = queryPart {
            expression1.notIn(listOf(bigDecimalExpression1, bigDecimalExpression2))
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notIn(
            value = expression1,
            compareValues = listOf(
                bigDecimalExpression1,
                bigDecimalExpression2,
            ),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notIn() with a subquery`() {
        // when
        val predicate = queryPart {
            expression1.notIn(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notIn(
            value = expression1,
            subquery = subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class WhereDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val expression1 = Paths.path(Book::isbn)

    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    private val predicate2 = Predicates.equal(
        Paths.path(Book::salePrice),
        Expressions.value(BigDecimal.valueOf(200)),
    )

    @Test
    fun `where() with a predicate`() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity1,
            ).where(
                predicate1,
            )
        }.toQuery()

        val actual: SelectQuery<Isbn> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = Isbn::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
            where = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `whereAnd() with predicates`() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity(Book::class),
            ).whereAnd(
                predicate1,
                null,
                predicate2,
            )
        }.toQuery()

        val actual: SelectQuery<Isbn> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = Isbn::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
            where = Predicates.and(
                listOf(
                    Predicates.parentheses(predicate1),
                    Predicates.parentheses(predicate2),
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `whereOr() with predicates`() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity1,
            ).whereOr(
                predicate1,
                null,
                predicate2,
            )
        }.toQuery()

        val actual: SelectQuery<Isbn> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = Isbn::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
            where = Predicates.or(
                listOf(
                    Predicates.parentheses(predicate1),
                    Predicates.parentheses(predicate2),
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class WhereDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val path1 = Paths.path(Book::price)

    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    private val predicate2 = Predicates.equal(
        Paths.path(Book::salePrice),
        Expressions.value(BigDecimal.valueOf(200)),
    )

    private val bigDecimal1 = BigDecimal.valueOf(100)

    @Test
    fun `where() with a predicate`() {
        // when
        val update = queryPart {
            update(
                entity1,
            ).set(
                path1,
                bigDecimal1,
            ).where(
                predicate1,
            )
        }.toQuery()

        val actual: UpdateQuery<Book> = update // for type check

        // then
        val expected = UpdateQueries.updateQuery(
            entity = entity1,
            set = mapOf(
                path1 to Expressions.value(bigDecimal1),
            ),
            where = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `whereAnd() with predicates`() {
        // when
        val update = queryPart {
            update(
                entity1,
            ).set(
                path1,
                bigDecimal1,
            ).whereAnd(
                predicate1,
                null,
                predicate2,
            )
        }.toQuery()

        val actual: UpdateQuery<Book> = update // for type check

        // then
        val expected = UpdateQueries.updateQuery(
            entity = entity1,
            set = mapOf(
                path1 to Expressions.value(bigDecimal1),
            ),
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
        val update = queryPart {
            update(
                entity1,
            ).set(
                path1,
                bigDecimal1,
            ).whereOr(
                predicate1,
                null,
                predicate2,
            )
        }.toQuery()

        val actual: UpdateQuery<Book> = update // for type check

        // then
        val expected = UpdateQueries.updateQuery(
            entity = entity1,
            set = mapOf(
                path1 to Expressions.value(bigDecimal1),
            ),
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

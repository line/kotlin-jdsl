package com.linecorp.kotlinjdsl.dsl.jpql.delete

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class WhereDslTest : AbstractJpqlDslTest() {
    private val entity1 = Entities.entity(Book::class)

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
        val delete = queryPart {
            deleteFrom(
                entity1,
            ).where(
                predicate1,
            )
        }.toQuery()

        val actual: DeleteQuery<Book> = delete // for type check

        // then
        val expected = Deletes.delete(
            entity = entity1,
            where = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `whereAnd() with predicates`() {
        // when
        val delete = queryPart {
            deleteFrom(
                entity(Book::class),
            ).whereAnd(
                predicate1,
                null,
                predicate2,
            )
        }.toQuery()

        val actual: DeleteQuery<Book> = delete // for type check

        // then
        val expected = Deletes.delete(
            entity = Entities.entity(Book::class),
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
        val delete = queryPart {
            deleteFrom(
                entity(Book::class),
            ).whereOr(
                predicate1,
                null,
                predicate2,
            )
        }.toQuery()

        val actual: DeleteQuery<Book> = delete // for type check

        // then
        val expected = Deletes.delete(
            entity = Entities.entity(Book::class),
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

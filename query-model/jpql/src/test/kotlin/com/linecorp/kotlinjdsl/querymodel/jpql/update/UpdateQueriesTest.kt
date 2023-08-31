package com.linecorp.kotlinjdsl.querymodel.jpql.update

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.update.impl.JpqlUpdateQuery
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class UpdateQueriesTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val path1 = Paths.path(Book::price)
    private val path2 = Paths.path(Book::salePrice)

    private val expression1 = Expressions.value(BigDecimal.valueOf(100))
    private val expression2 = Expressions.value(BigDecimal.valueOf(200))

    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    @Test
    fun updateQuery() {
        // when
        val actual = UpdateQueries.updateQuery(
            entity = entity1,
            set = mapOf(
                path1 to expression1,
                path2 to expression2,
            ),
            where = predicate1,
        )

        // then
        val expected = JpqlUpdateQuery(
            entity = entity1,
            set = mapOf(
                path1 to expression1,
                path2 to expression2,
            ),
            where = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

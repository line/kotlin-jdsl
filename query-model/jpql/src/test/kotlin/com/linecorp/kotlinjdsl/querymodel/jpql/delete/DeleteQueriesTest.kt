package com.linecorp.kotlinjdsl.querymodel.jpql.delete

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DeleteQueriesTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    @Test
    fun deleteQuery() {
        // when
        val actual = DeleteQueries.deleteQuery(
            entity = entity1,
            where = predicate1,
        )

        // then
        val expected = JpqlDeleteQuery(
            entity = entity1,
            where = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}

package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class GroupByDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val expression1 = Paths.path(Book::title)
    private val expression2 = Paths.path(Book::isbn)

    @Test
    fun groupBy() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity1,
            ).groupBy(
                expression1,
                null,
                expression2,
            )
        }.toQuery()

        val actual: SelectQuery<String> = select // for type check

        // then
        val expected = SelectQueries.select(
            returnType = String::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
            groupBy = listOf(expression1, expression2),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
